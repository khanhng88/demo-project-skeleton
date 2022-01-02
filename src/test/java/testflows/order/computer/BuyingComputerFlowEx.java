package testflows.order.computer;

import models.components.cart.AbstractShoppingCartComp;
import models.components.cart.ShoppingCartItemComponent;
import models.components.cart.SummaryShoppingCartComp;
import models.components.product.ComputerEssentialComponent;
import models.pages.CheckOutOptionPage;
import models.pages.CheckOutPage;
import models.pages.CompleteCheckOutPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.computer.ComputerDataObject;
import testdata.purchasing.computer.ComputerSpec;
import testdata.purchasing.computer.ComputerType;
import testdata.user.UserData;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BuyingComputerFlowEx<T extends ComputerEssentialComponent> implements ComputerPriceType{

    private final WebDriver driver;
    private T essentialCompGeneric;
    private Map<ComputerType, ComputerOrder> allComputerData;
    double subTotal = 0.0;

    public BuyingComputerFlowEx(WebDriver driver) {
        this.driver = driver;
        allComputerData = new HashMap<>();
    }

    public BuyingComputerFlowEx<T> withComputerEssentialComp(Class<T> computerType) {
        try {
            essentialCompGeneric = computerType.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public void buildComputer(ComputerType computerType, ComputerDataObject compData, int quantity) {
        //add computer type, computer data and quantity into computerOrder object
        ComputerOrder computerOrder = new ComputerOrder(computerType, compData, quantity);
        if (essentialCompGeneric == null) {
            throw new RuntimeException("Please call withComputerType to specify computer type!");
        }
        
        ItemDetailsPage detailsPage = new ItemDetailsPage(driver);

        // Build Comp specs
        essentialCompGeneric.selectProcessorType(compData.getProcessorType());
        essentialCompGeneric.selectRAM(compData.getRam());
        essentialCompGeneric.selectHDD(compData.getHdd());
        essentialCompGeneric.selectSoftware(compData.getSoftware());
        essentialCompGeneric.setQuantity(quantity);
        compData.setDefaultPrice(essentialCompGeneric.getDefaultPrice());

        // Add To cart
        essentialCompGeneric.clickOnAddToCartBtn();
        try {
            detailsPage.waitUntilItemAddedToCart();
            //add computer type and computer order into map allComputerData
            allComputerData.put(computerType, computerOrder);
        } catch (Exception e) {
            throw new Error("[ERR] Item is not added after 15s!");
        }
    }

    public void verifyComputerAdded() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        allComputerData.keySet().forEach(computerType -> {
            double startPrice = 0.0;
            switch (computerType){
                case CHEAP_COMPUTER:
                    startPrice = cheapComputerPrice;
                    break;
                case STANDARD_COMPUTER:
                    startPrice = standardComputerPrice;
                    break;
                default:
                    Assert.fail("Missing computer start price for "+computerType.type());
            }
            //get list of data row that matches computer type
            Optional<ShoppingCartItemComponent.CartItemRowData> cartItemRow =
                shoppingCartPage.shoppingCartItemComp().cartItemRowDataList().stream()
                        .filter(cartItemRowData ->cartItemRowData.getProductName().equals(computerType.type())).findFirst();

            ShoppingCartItemComponent.CartItemRowData cartItemRowData = cartItemRow.orElse(null);

            if(cartItemRowData!=null) {
                ComputerOrder computerOrder = allComputerData.get(computerType);
                double orderPrice = calculateItemPrice(computerOrder.computerDataObject, startPrice) * computerOrder.quantity();
                subTotal +=orderPrice;

                System.out.println("Subtotal of all items: "+subTotal);

                Assert.assertEquals(orderPrice, cartItemRowData.getPrice()*computerOrder.quantity,
                        "[ERR] incorrect item price");
                Assert.assertTrue(cartItemRowData.getProductAttributes()
                                .contains(ComputerSpec.valueOf(computerOrder.computerDataObject.getHdd()).value()),
                        "[ERR] not matched HDD value");

                Assert.assertTrue(cartItemRowData.getProductAttributes()
                                .contains(ComputerSpec.valueOf(computerOrder.computerDataObject.getProcessorType()).value()),
                        "[ERR] not matched processor type value");
            }else {
                Assert.fail("[ERR] The computer with type " + computerType.type() + " was not added. ");
            }

        });

        double footerSubTotalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.subTotal).doubleValue();
        double footerTotalPrice = shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.total).doubleValue();
        double expectedFooterTotalPrice = footerSubTotalPrice
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.tax).doubleValue()
                + shoppingCartPage.cartTotalComponent().buildMapPrice().get(ComputerPriceType.shipping).doubleValue();

        Assert.assertEquals(footerSubTotalPrice, subTotal, "[ERR] Current sub total price at footer is incorrect.");
        Assert.assertEquals(footerTotalPrice, expectedFooterTotalPrice, "[ERR] Current total price at footer is incorrect.");

        shoppingCartPage.cartTotalComponent().termOfServicebtn().click();
        shoppingCartPage.cartTotalComponent().checkOutBtn().click();
        CheckOutOptionPage checkOutOptionPage = new CheckOutOptionPage(driver);
        checkOutOptionPage.asGuestOrRegisteredUser().checkOutAsGuestBtn().click();
    }

    /**
     * calculate price of each item
     * @param computerDataObject
     * @param startPrice
     * @return
     */
    private double calculateItemPrice(ComputerDataObject computerDataObject, double startPrice) {
        double additionalFees = 0.0;
        additionalFees = additionalFees + ComputerSpec.valueOf(computerDataObject.getProcessorType()).additionPrice()
                + ComputerSpec.valueOf(computerDataObject.getRam()).additionPrice()
                + ComputerSpec.valueOf(computerDataObject.getHdd()).additionPrice();
        if(computerDataObject.getSoftware()!=null) {
            additionalFees += ComputerSpec.valueOf(computerDataObject.getSoftware()).additionPrice();
        }

        return startPrice + additionalFees;
    }

    public void goToCheckOutOptions(UserData userData, ComputerDataObject computerDataObject) {
        CheckOutPage checkOutPage = new CheckOutPage(this.driver);

        //filling billing info
        checkOutPage.billingAddressComp().firstName().sendKeys(userData.getFirstName());
        checkOutPage.billingAddressComp().lastName().sendKeys(userData.getLastName());
        checkOutPage.billingAddressComp().email().sendKeys(userData.getEmail());
        checkOutPage.billingAddressComp().company().sendKeys(userData.getCompany());
        checkOutPage.billingAddressComp().selectCountry(userData.getCountry());
        checkOutPage.billingAddressComp().selectState(userData.getState());
        checkOutPage.billingAddressComp().city().sendKeys(userData.getCity());
        checkOutPage.billingAddressComp().address1().sendKeys(userData.getAddress1());
        checkOutPage.billingAddressComp().zip().sendKeys(userData.getZip());
        checkOutPage.billingAddressComp().phone().sendKeys(userData.getPhone());


        checkOutPage.billingAddressComp().continueBtn().click();
        checkOutPage.shippingAddressComp().continueBtn().click();

        //select shipping method
        checkOutPage.shippingMethodComp().twoDayShippingOption().click();
        checkOutPage.shippingMethodComp().continueBtn().click();

        //select payment method
        checkOutPage.paymentMethodComp().cashOnDelivery().click();
        checkOutPage.paymentMethodComp().continueBtn().click();

        // and continue
        checkOutPage.paymentInformationComp().continueBtn().click();

        //check and review user and shipping/payment info
        //billing section
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getName().getText().trim(), userData.getFirstName()+
                " "+userData.getLastName(), "[ERR] Name in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getEmail().getText().trim(), "Email: "+userData.getEmail(),
                "[ERR] email in Billing section  is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getCompany().getText().trim(), userData.getCompany(),
                "[ERR] Company in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getAddress1().getText().trim(), userData.getAddress1(),
                "[ERR] Address1  in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getCountry().getText().trim(), userData.getCountry(),
                "[ERR] Country  in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getCityStateZip().getText().trim(), userData.getCity()
                +" , "+userData.getZip(),"[ERR] City State Zip info in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().billingInfoComp().getPhone().getText().trim(), "Phone: "+userData.getPhone(),
                "[ERR] Phone number in Billing section is not matched.");
        //check payment method info
        Assert.assertTrue(checkOutPage.paymentMethodComp().cashOnDeliveryLabel().getAttribute("innerHTML").trim().
                contains(checkOutPage.confirmOrderComp().billingInfoComp().getPaymentMethod().getText().trim())
                , "[ERR] Payment method in Billing section is not matched.");


        //shipping section
        Assert.assertEquals(checkOutPage.confirmOrderComp().shippingInfoComp().getName().getText(), userData.getFirstName()+
                " "+userData.getLastName(), "[ERR] Name in Shipping section is not matched.");
        Assert.assertTrue(checkOutPage.confirmOrderComp().shippingInfoComp().getEmail().getText().contains(userData.getEmail()),
                "[ERR] email in Billing section  is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().shippingInfoComp().getCompany().getText(), userData.getCompany(),
                "[ERR] Company in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().shippingInfoComp().getAddress1().getText(), userData.getAddress1(),
                "[ERR] Address1  in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().shippingInfoComp().getCountry().getText(), userData.getCountry(),
                "[ERR] Country  in Billing section is not matched.");
        Assert.assertEquals(checkOutPage.confirmOrderComp().shippingInfoComp().getCityStateZip().getText(), userData.getCity()
                +" , "+userData.getZip(),"[ERR] City State Zip info in Billing section is not matched.");
        Assert.assertTrue(checkOutPage.confirmOrderComp().shippingInfoComp().getPhone().getText().contains(userData.getPhone()),
                "[ERR] Phone number in Billing section is not matched.");
        //check shipping method info
        Assert.assertTrue(checkOutPage.shippingMethodComp().twoDayShippingOptionlabel().getAttribute("innerHTML").trim().
                        contains(checkOutPage.confirmOrderComp().shippingInfoComp().getShippingMethod().getText().trim()),
                "[ERR] Shipping method in Shipping section is not matched.");

        //check cart item row in complete checkout page
        double expectedFooterSubTotalPrice = 0.0;
        for(SummaryShoppingCartComp.CartItemRowData cartItemRowData: checkOutPage.confirmOrderComp().summaryShoppingCartComp().cartItemRowDataList()) {
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(computerDataObject.getProcessorType()).value()),
                    "[ERR] Processor type is not matched.");
            Assert.assertTrue(cartItemRowData.getProductAttributes().replaceAll("\\s", "").contains(ComputerSpec.valueOf(computerDataObject.getRam()).value().replaceAll("\\s", "")),
                    "[ERR] Ram value is not matched.");
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(computerDataObject.getHdd()).value()),
                    "[ERR] HDD value is not matched.");
            expectedFooterSubTotalPrice = cartItemRowData.getPrice() * cartItemRowData.getQuantity();
            Assert.assertEquals(cartItemRowData.getSubTotal(), expectedFooterSubTotalPrice,
                    "[ERR] Subtotal is unmatched.");
        }

        //check cart footer info
        double expectedFooterTotalPrice = 0.0;
        Map<String, Double> priceMap = checkOutPage.confirmOrderComp().cartFooterComponent().
                cartTotalComponent().buildMapPrice();
        priceMap.remove(ComputerPriceType.total);
        for(String key : priceMap.keySet()) {
            System.out.println(key+" : "+priceMap.get(key));
            expectedFooterTotalPrice +=priceMap.get(key).doubleValue();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Assert.assertEquals(checkOutPage.confirmOrderComp().cartFooterComponent().cartTotalComponent().
                buildMapPrice().get(ComputerPriceType.total).doubleValue(),expectedFooterTotalPrice,
                "[ERR] Total value is not matched");

        //confirm order
        checkOutPage.confirmOrderComp().confirmBtn().click();
        checkOutPage.confirmOrderComp().finishBtn().click();
    }

    public void completeCheckOut() {
        CompleteCheckOutPage completeCheckOutPage = new CompleteCheckOutPage(driver);
        Assert.assertTrue(completeCheckOutPage.pageTitle().getText().contains("Thank you"),
                "[ERR] Wrong title");
        Assert.assertTrue(completeCheckOutPage.completedCheckOutDataComp().orderDetailLink().getAttribute("href").
                contains(completeCheckOutPage.completedCheckOutDataComp().orderNum()),"[ERR] The order number is not matched.");

        completeCheckOutPage.completedCheckOutDataComp().continueBtn().click();
    }

    public static class ComputerOrder{
        private ComputerType computerType;
        private ComputerDataObject computerDataObject;
        private int quantity;

        public ComputerOrder(ComputerType computerType, ComputerDataObject computerDataObject, int quantity) {
            this.computerType = computerType;
            this.computerDataObject = computerDataObject;
            this.quantity = quantity;
        }

        public ComputerType computerType() {
            return this.computerType;
        }

        public ComputerDataObject computerDataObject() {
            return this.computerDataObject;
        }

        public int quantity() {
            return this.quantity;
        }
    }
}
