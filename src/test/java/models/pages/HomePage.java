package models.pages;

import driver.Driver;
import models.components.global.FooterComponent;
import models.components.global.HeaderComponent;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;
    private HeaderComponent headerComponent;
    private FooterComponent footerComponent;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HeaderComponent headerComponent(){
        return new HeaderComponent(this.driver);
    }

    public FooterComponent footerComponent(){
        return new FooterComponent(this.driver);
    }


    public static void main(String[] args) {
        WebDriver driver = Driver.getChromeDriver();
        try {
            HomePage homePage = new HomePage(driver);
            driver.get("http://demowebshop.tricentis.com/");

            String headerImgSrc = homePage.headerComponent().logoImg().getAttribute("src");
            System.out.println("Logo Img src: " + headerImgSrc);
            homePage.headerComponent().inputProductNameAndSearch("Lap top");

            Thread.sleep(3000);

            String poweredByText = homePage.footerComponent().poweredByComponent().getText();
            System.out.println("Powered by: " + poweredByText);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

}

