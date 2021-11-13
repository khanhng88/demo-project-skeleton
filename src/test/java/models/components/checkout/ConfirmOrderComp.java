package models.components.checkout;

import models.components.cart.SummaryShoppingCartComp;
import models.components.cart.footer.CartFooterComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrderComp {
    private final WebDriver driver;
    private By confirmSel = By.cssSelector("#confirm-order-buttons-container >input");
    private By finishSel = By.className("confirm-order-next-step-button");

    public ConfirmOrderComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement confirmBtn() {
        return driver.findElement(confirmSel);
    }

    public WebElement finishBtn() {
        return driver.findElement(finishSel);
    }

    public BillingInfoComp billingInfoComp() {
        return new BillingInfoComp(driver);
    }

    public ShippingInfoComp shippingInfoComp() {
        return new ShippingInfoComp(driver);
    }

    public SummaryShoppingCartComp summaryShoppingCartComp() {
        return new SummaryShoppingCartComp(driver);
    }

    public CartFooterComponent cartFooterComponent() {
        return new CartFooterComponent(driver);
    }

    private static abstract class InfomationComp {
        private final WebDriver driver;
        protected WebElement comp;

        //common selectors inside Information component
        private By nameSel = By.className("name");
        private By emailSel = By.className("email");
        private By phoneSel = By.className("phone");
        private By faxSel = By.className("fax");
        private By companySel = By.className("company");
        private By address1Sel = By.className("address1");
        private By cityStateZipSel = By.className("city-state-zip");
        private By countrySel = By.className("country");

        public InfomationComp(WebDriver driver) {
            this.driver = driver;
            this.comp = driver.findElement(compSel());
        }

        abstract By compSel();

        public WebElement getName() {
            return this.comp.findElement(nameSel);
        }

        public WebElement getEmail() {
            return this.comp.findElement(emailSel);
        }

        public WebElement getCompany() {
            return this.comp.findElement(companySel);
        }

        public WebElement getAddress1() {
            return this.comp.findElement(address1Sel);
        }

        public WebElement getCityStateZip() {
            return this.comp.findElement(cityStateZipSel);
        }

        public WebElement getCountry() {
            return this.comp.findElement(countrySel);
        }

        public WebElement getPhone() {
            return this.comp.findElement(phoneSel);
        }

        public WebElement getFax() {
            return this.comp.findElement(faxSel);
        }


    }

    //create 2 inner classes to handle Shipping info and Billing info

    public static class BillingInfoComp extends InfomationComp {
        private By paymentMethodSel = By.className("payment-method");

        public BillingInfoComp(WebDriver driver) {
            super(driver);
        }

        @Override
        By compSel() {
            return By.className("billing-info");
        }

        public WebElement getPaymentMethod() {
            return this.comp.findElement(paymentMethodSel);
        }
    }

    public static class ShippingInfoComp extends InfomationComp {
        private By shippingMethodSel = By.className("shipping-method");

        public ShippingInfoComp(WebDriver driver) {
            super(driver);
        }

        @Override
        By compSel() {
            return By.className("shipping-info");
        }

        public WebElement getShippingMethod() {
            return this.comp.findElement(shippingMethodSel);
        }
    }
}
