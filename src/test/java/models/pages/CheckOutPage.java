package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckOutPage {
    private final WebDriver driver;
    private BillingAddressComp billingAddressComp;
    private ShippingAddressComp shippingAddressComp;
    private ShippingMethodComp shippingMethodComp;
    private PaymentMethodComp paymentMethodComp;
    private PaymentInformationComp paymentInformationComp;
    private ConfirmOrderComp confirmOrderComp;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    public BillingAddressComp billingAddressComp() {
        return new BillingAddressComp(driver);
    }

    public ShippingAddressComp shippingAddressComp() {
        return new ShippingAddressComp(driver);
    }

    public ShippingMethodComp shippingMethodComp() {
        return new ShippingMethodComp(driver);
    }

    public PaymentMethodComp paymentMethodComp() {
        return new PaymentMethodComp(driver);
    }

    public PaymentInformationComp paymentInformationComp() {
        return new PaymentInformationComp(driver);
    }

    public ConfirmOrderComp confirmOrderComp() {
        return new ConfirmOrderComp(driver);
    }
}
