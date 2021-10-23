package models.pages.cart;

import models.components.cart.ShoppingCartItemComponent;
import models.components.cart.footer.CartFooterComponent;
import models.components.cart.footer.CartTotalComponent;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage {

    private final WebDriver driver;
    private CartTotalComponent cartTotalComponent;
    private CartFooterComponent cartFooterComponent;
    private ShoppingCartItemComponent shoppingCartItemComponent;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public ShoppingCartItemComponent shoppingCartItemComp(){
        if(shoppingCartItemComponent == null) {
            shoppingCartItemComponent = new ShoppingCartItemComponent(driver);
        }
        return shoppingCartItemComponent;
    }

    public CartTotalComponent cartTotalComponent() {
        if(cartTotalComponent == null) {
            cartTotalComponent = new CartTotalComponent(driver);
        }
        return cartTotalComponent;
    }

    public CartFooterComponent cartFooterComponent() {
        if(cartFooterComponent == null) {
            cartFooterComponent = new CartFooterComponent(driver);
        }
        return cartFooterComponent;
    }
}
