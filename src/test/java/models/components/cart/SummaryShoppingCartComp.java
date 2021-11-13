package models.components.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SummaryShoppingCartComp extends AbstractShoppingCartComp{
    public SummaryShoppingCartComp(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By productQuantitySel() {
        return By.className("qty");
    }

    @Override
    protected Boolean isSummaryCartComp() {
        return true;
    }
}
