package models.components.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItemComponent extends AbstractShoppingCartComp{

    public ShoppingCartItemComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By productQuantitySel() {
        return By.className("qty-input");
    }

    @Override
    protected Boolean isSummaryCartComp() {
        return false;
    }
}
