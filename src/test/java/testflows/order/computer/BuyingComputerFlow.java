package testflows.order.computer;

import models.components.product.ComputerEssentialComponent;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerSpec;

import java.lang.reflect.InvocationTargetException;

public class BuyingComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private T essentialCompGeneric;

    public BuyingComputerFlow(WebDriver driver) {
        this.driver = driver;
    }

    public BuyingComputerFlow<T> withComputerEssentialComp(Class<T> computerType) {
        try {
            essentialCompGeneric = computerType.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public void buildComputer(ComputerDataObject compData) {
        if (essentialCompGeneric == null) {
            throw new RuntimeException("Please call withComputerType to specify computer type!");
        }
        ItemDetailsPage detailsPage = new ItemDetailsPage(driver);

        // Build Comp specs
        essentialCompGeneric.selectProcessorType(compData.getProcessorType());
        essentialCompGeneric.selectRAM(compData.getRam());
        essentialCompGeneric.selectHDD(compData.getHdd());
        essentialCompGeneric.selectSoftware(compData.getSoftware());
        compData.setDefaultPrice(essentialCompGeneric.getDefaultPrice());

        // Add To cart
        essentialCompGeneric.clickOnAddToCartBtn();
        try {
            detailsPage.waitUntilItemAddedToCart();
        } catch (Exception e) {
            throw new Error("[ERR] Item is not added after 15s!");
        }
    }

    public void verifyComputerAdded(ComputerDataObject simpleComputer) {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        // TODO: need to handle this price
        final double fixedPrice = 800.0;

        // Get additional fee
        double additionalFees = ComputerSpec.valueOf(simpleComputer.getProcessorType()).additionPrice()
            + ComputerSpec.valueOf(simpleComputer.getRam()).additionPrice()
            + ComputerSpec.valueOf(simpleComputer.getHdd()).additionPrice();

        if(simpleComputer.getSoftware() != null) {
            additionalFees += ComputerSpec.valueOf(simpleComputer.getSoftware()).additionPrice();
        }

        // verify computer price with options with sub-Total price
        double currentCompPrice = simpleComputer.getDefaultPrice() + additionalFees;
        double subTotalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.subTotal).doubleValue();
        System.out.println(subTotalPrice);
        Assert.assertEquals(currentCompPrice, subTotalPrice,"[ERR] Sub Total price is not correct!");

        //verify Total price (included ship and tax)
        double currentTotalPrice = subTotalPrice
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.tax).doubleValue()
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.shipping).doubleValue();

        double totalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.total).doubleValue();
        // Compare
        Assert.assertEquals(totalPrice, currentTotalPrice, "[ERR] Total price is not correct!");
        shoppingCartPage.cartTotalComponent().checkout();
    }
}
