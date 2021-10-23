package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentInformationComp {
    private final WebDriver driver;
    private By continueSel = By.cssSelector("#payment-info-buttons-container >input");

    public PaymentInformationComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return driver.findElement(continueSel);
    }
}
