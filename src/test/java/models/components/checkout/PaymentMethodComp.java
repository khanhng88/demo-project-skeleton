package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentMethodComp {
    private final WebDriver driver;
    private By cashOndelieverySel = By.cssSelector("[value=\"Payments.CashOnDelivery\"]");
    private By cashOndelieverylblSel = By.xpath("following-sibling::*");
    private By checkMoneyOrderSel = By.cssSelector("[value=\"Payments.CheckMoneyOrder\"]");
    private By creditCardSel = By.cssSelector("[value=\"Payments.Manual\"]");
    private By purchaseOrderSel = By.cssSelector("[value=\"Payments.PurchaseOrder\"]");
    private By continueSel = By.cssSelector("#payment-method-buttons-container >input");

    public PaymentMethodComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return driver.findElement(continueSel);
    }

    public WebElement cashOnDelivery() {
        return driver.findElement(cashOndelieverySel);
    }

    public WebElement cashOnDeliveryLabel() {
        return cashOnDelivery().findElement(cashOndelieverylblSel);
    }

    public WebElement checkMoneyOrder() {
        return driver.findElement(checkMoneyOrderSel);
    }

    public WebElement creditCard() {
        return driver.findElement(creditCardSel);
    }

    public WebElement purchaseOrder() {
        return driver.findElement(purchaseOrderSel);
    }
}
