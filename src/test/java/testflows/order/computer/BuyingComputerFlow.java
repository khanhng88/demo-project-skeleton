package testflows.order.computer;

import models.components.cart.CartItemRowData;
import models.components.product.ComputerEssentialComponent;
import models.pages.CheckOutOptionPage;
import models.pages.CheckOutPage;
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

        //verify cart item : product name, link, attributes, price, subtotal price, total prices
        double expectedSubTotalPrice = simpleComputer.getDefaultPrice() + additionalFees;
        
        for(CartItemRowData cartItemRowData : shoppingCartPage.shoppingCartItemComp().cartItemRowDataList()) {
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(simpleComputer.getProcessorType()).value()),
                    "[ERR] Processor Type is not matched.");
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(simpleComputer.getHdd()).value()),
                    "[ERR] HDD value is not matched.");

            Assert.assertNotNull(cartItemRowData.getProductName(), "[ERR] Product name is null.");
            Assert.assertNotNull(cartItemRowData.getProductLink(), "[ERR] Product link is null.");

            Assert.assertEquals(cartItemRowData.getPrice(),expectedSubTotalPrice, "[ERR] Total Cart Item Price is not matched.");
        }
        double footerSubTotalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.subTotal).doubleValue();
        double footerTotalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.total).doubleValue();
        double expectedFooterTotalPrice = footerSubTotalPrice
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.tax).doubleValue()
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.shipping).doubleValue();
        Assert.assertEquals(footerSubTotalPrice, expectedSubTotalPrice, "[ERR] Current total price at footer is incorrect.");
        Assert.assertEquals(footerTotalPrice, expectedFooterTotalPrice, "[ERR] Current total price at footer is incorrect.");

        shoppingCartPage.cartTotalComponent().termOfServicebtn().click();
        shoppingCartPage.cartTotalComponent().checkOutBtn().click();
        CheckOutOptionPage checkOutOptionPage = new CheckOutOptionPage(driver);
        checkOutOptionPage.asGuestOrRegisteredUser().checkOutAsGuestBtn().click();

        //Go through check out steps
        checkOutSteps();
    }

    public void checkOutSteps() {
        CheckOutPage checkOutPage = new CheckOutPage(this.driver);

        //filling billing info
        checkOutPage.billingAddressComp().firstName().sendKeys("Khanh");
        checkOutPage.billingAddressComp().lastName().sendKeys("Nguyen");
        checkOutPage.billingAddressComp().email().sendKeys("khanh@mail.com");
        checkOutPage.billingAddressComp().company().sendKeys("ABC");
        checkOutPage.billingAddressComp().selectCountry("Viet Nam");
        checkOutPage.billingAddressComp().selectState("Other (Non US)");
        checkOutPage.billingAddressComp().city().sendKeys("Ho Chi Minh");
        checkOutPage.billingAddressComp().address1().sendKeys("8 Phan Xich Long Ward 3 Phu Nhuan Dist");
        checkOutPage.billingAddressComp().zip().sendKeys("70000");
        checkOutPage.billingAddressComp().phone().sendKeys("0946537203");
        checkOutPage.billingAddressComp().continueBtn().click();
        checkOutPage.shippingAddressComp().continueBtn().click();

        //select shipping method
        checkOutPage.shippingMethodComp().twoDayShippingOption().click();
        checkOutPage.shippingMethodComp().continueBtn().click();

        //select payment method
        checkOutPage.paymentMethodComp().cashOnDelivery().click();
        checkOutPage.paymentMethodComp().continueBtn().click();

        //review payment info and continue
        checkOutPage.paymentInformationComp().continueBtn().click();

        //confirm order
        checkOutPage.confirmOrderComp().confirmBtn().click();
        checkOutPage.confirmOrderComp().finishBtn().click();
    }


}
