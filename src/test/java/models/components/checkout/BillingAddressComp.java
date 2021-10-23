package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BillingAddressComp {
    private final WebDriver driver;
    private By firstNameSel = By.id("BillingNewAddress_FirstName");
    private By lastNameSel = By.id("BillingNewAddress_LastName");
    private By emailSel = By.id("BillingNewAddress_Email");
    private By companySel = By.id("BillingNewAddress_Company");
    private By countrySel = By.id("BillingNewAddress_CountryId");
    private By stateSel = By.id("BillingNewAddress_StateProvinceId");
    private By citySel = By.id("BillingNewAddress_City");
    private By address1Sel = By.id("BillingNewAddress_Address1");
    private By zipSel = By.id("BillingNewAddress_ZipPostalCode");
    private By phoneSel = By.id("BillingNewAddress_PhoneNumber");
    private By continueSel = By.cssSelector(".new-address-next-step-button");

    public BillingAddressComp(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return driver.findElement(continueSel);
    }

    public WebElement firstName() {
        return driver.findElement(firstNameSel);
    }

    public WebElement lastName() {
        return driver.findElement(lastNameSel);
    }

    public WebElement email() {
        return driver.findElement(emailSel);
    }

    public WebElement company() {
        return driver.findElement(companySel);
    }

    public void selectCountry(String country) {
        Select select = new Select(driver.findElement(countrySel));
        select.selectByVisibleText(country);
    }

    public void selectState(String state) {
        Select select = new Select(driver.findElement(stateSel));
        select.selectByVisibleText(state);
    }

    public WebElement city() {
        return driver.findElement(citySel);
    }

    public WebElement address1() {
        return driver.findElement(address1Sel);
    }

    public WebElement zip() {
        return driver.findElement(zipSel);
    }

    public WebElement phone() {
        return driver.findElement(phoneSel);
    }
}
