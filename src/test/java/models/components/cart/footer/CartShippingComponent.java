package models.components.cart.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartShippingComponent {
    private final WebDriver driver;
    private By selectCountrySel = By.cssSelector("select[id='CountryId']");
    private By selectStateProvinceSel = By.id("StateProvinceId");
    private By zipCodeSel = By.id("ZipPostalCode");
    private By btnEstimateShipping = By.cssSelector("input[name='estimateshipping']");

    public CartShippingComponent(WebDriver driver) {
        this.driver = driver;
    }

}
