package models.components.cart.footer;

import org.openqa.selenium.WebDriver;

public class CartFooterComponent {
    private final WebDriver driver;

    public CartFooterComponent(WebDriver driver) {
        this.driver = driver;
    }

    public CartShippingComponent getCartShippingComponent() {
        return new CartShippingComponent(driver);
    }

    public CartTotalComponent getCartTotalComponent() {
        return new CartTotalComponent(driver);
    }
}
