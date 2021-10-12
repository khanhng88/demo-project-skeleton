package models.components.global;

import models.components.global.footer.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterComponent {

    private final WebDriver driver;
    private MenuInformationComponent menuInformationComponent;
    private MenuCustomerServiceComponent menuCustomerServiceComponent;
    private MenuMyAccountComponent menuMyAccountComponent;
    private MenuFollowUsComponent menuFollowUsComponent;
    private PoweredByComponent poweredByComponent;
    private final By disclaimerTextSel = By.cssSelector(".footer-disclaimer");

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
    }

    public MenuInformationComponent menuInformationComponent(){
        return new MenuInformationComponent(driver);
    }

    public MenuCustomerServiceComponent menuCustomerServiceComponent(){
        return new MenuCustomerServiceComponent(driver);
    }

    public MenuMyAccountComponent menuMyAccountComponent(){
        return new MenuMyAccountComponent(driver);
    }

    public MenuFollowUsComponent menuFollowUsComponent(){
        return new MenuFollowUsComponent(driver);
    }

    public PoweredByComponent poweredByComponent(){
        return new PoweredByComponent(driver);
    }

    public String copyrightText(){
        return this.driver.findElement(disclaimerTextSel).getText();
    }
}
