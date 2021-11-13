package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingMethodComp {
    private final WebDriver driver;
    private By groundShippingOptionSel = By.cssSelector("[value='Ground___Shipping.FixedRate']");
    private By nextDayAirShippingOptionSel = By.cssSelector("[value='Next Day Air___Shipping.FixedRate']");
    private By twoDayShippingOptionSel = By.cssSelector("[value='2nd Day Air___Shipping.FixedRate']");
    private By twoDayShippingOptionlblSel = By.xpath("following-sibling::*");
    private By continueSel = By.cssSelector("#shipping-method-buttons-container >input");

    public ShippingMethodComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return driver.findElement(continueSel);
    }

    public WebElement groundShippingOption() {
        return driver.findElement(groundShippingOptionSel);
    }

    public WebElement nextDayAirShippingOption() {
        return driver.findElement(nextDayAirShippingOptionSel);
    }

    public WebElement twoDayShippingOption() {
        return driver.findElement(twoDayShippingOptionSel);
    }

    public WebElement twoDayShippingOptionlabel() {
        return twoDayShippingOption().findElement(twoDayShippingOptionlblSel);
    }
}
