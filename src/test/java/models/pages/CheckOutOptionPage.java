package models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOutOptionPage {
    private final WebDriver driver;

    public CheckOutOptionPage(WebDriver driver) {
        this.driver = driver;
    }

    public AsGuestOrRegisteredUserComponent asGuestOrRegisteredUser() {
        return new AsGuestOrRegisteredUserComponent(driver);
    }


    public static class AsGuestOrRegisteredUserComponent {
        private final WebDriver driver;
        private By checkOutAsGuestSel = By.className("checkout-as-guest-button");

        public AsGuestOrRegisteredUserComponent(WebDriver driver) {
            this.driver = driver;
        }

        public WebElement checkOutAsGuestBtn() {
            return driver.findElement(checkOutAsGuestSel);
        }
    }
}
