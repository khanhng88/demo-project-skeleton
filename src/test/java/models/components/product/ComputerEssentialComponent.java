package models.components.product;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testdata.purchasing.computer.ComputerSpec;

public abstract class ComputerEssentialComponent {

    private final WebDriver driver;
    private final By addToCartBtnSel = By.cssSelector("[id^='add-to-cart-button']");
    private final By productQuantitySel = By.className("qty-input");

    public ComputerEssentialComponent(WebDriver driver) {
        this.driver = driver;
    }

    public abstract void selectProcessorType(String type);
    public abstract void selectRAM(String type);

    @Step("Select HDD with value {type}")
    public void selectHDD(String type) {
        selectCompSpecOption(type);
    }

    public void selectSoftware(String type) {
        selectCompSoftwareSpecOption(type);
    }

    protected void selectCompSpecOption(String option){
        String optionValue =  ComputerSpec.valueOf(option).value();
        String selectorString = "//label[contains(text(), \"" + optionValue + "\")]";
        By optionSel = By.xpath(selectorString);
        driver.findElement(optionSel).click();
    }

    @Step("Click on [Add to cart]")
    public void clickOnAddToCartBtn(){
        driver.findElement(addToCartBtnSel).click();
    }

    public double getDefaultPrice() {
        WebElement defaultPriceEl = driver.findElement(By.cssSelector(".product-price"));
        return Double.parseDouble(defaultPriceEl.getText());

    }

    private void selectCompSoftwareSpecOption(String option) {
        if(option == null) {
            return;
        }
        String optionValue =  ComputerSpec.valueOf(option).value();
        String selectorString = "//label[contains(text(), \"" + optionValue + "\")]/preceding-sibling::input";
        By optionSel = By.xpath(selectorString);
        if(driver.findElement(optionSel).isSelected()) {
            return;
        }
        driver.findElement(optionSel).click();
    }

    public void setQuantity(int quantity) {
        WebElement productQuantityElement = driver.findElement(productQuantitySel);
        productQuantityElement.clear();
        productQuantityElement.sendKeys(String.valueOf(quantity));

    }

}
