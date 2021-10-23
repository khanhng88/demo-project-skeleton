package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrderComp {
    private final WebDriver driver;
    private By confirmSel = By.cssSelector("#confirm-order-buttons-container >input");
    private By finishSel = By.className("order-completed-continue-button");

    public ConfirmOrderComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement confirmBtn() {
        return driver.findElement(confirmSel);
    }

    public WebElement finishBtn() {
        return driver.findElement(finishSel);
    }
}
