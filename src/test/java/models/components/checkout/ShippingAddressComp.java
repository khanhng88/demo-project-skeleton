package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingAddressComp {
    private final WebDriver driver;
    private By continueSel = By.cssSelector("#shipping-buttons-container >input");

    public ShippingAddressComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return driver.findElement(continueSel);
    }
}
