package models.components.cart.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartTotalComponent {
    private final WebDriver driver;
    private By cartTotalSectionRowsSel = By.cssSelector(".cart-total tr");
    private By termOfServicesSel = By.cssSelector("input[id='termsofservice']");
    private By checkOutSel = By.cssSelector("button[id='checkout']");

    public CartTotalComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement termOfServicebtn() {
        return driver.findElement(termOfServicesSel);
    }

    public WebElement checkOutBtn() {
        return driver.findElement(checkOutSel);
    }

    public Map<String, Double> buildMapPrice() {
        List<WebElement> cartTotalRows = driver.findElements(cartTotalSectionRowsSel);
        Map<String, Double> mapPrice = new HashMap<>();
        for(WebElement row : cartTotalRows) {
            WebElement priceTypeElement = row.findElement(By.cssSelector(".cart-total-left"));
            WebElement priceValueElement = row.findElement(By.cssSelector(".cart-total-right"));
            mapPrice.put(priceTypeElement.getText().replace(":",""),Double.parseDouble(priceValueElement.getText()));
        }
        return mapPrice;
    }

    public void checkout() {
        termOfServicebtn().click();
        checkOutBtn().click();
    }
}
