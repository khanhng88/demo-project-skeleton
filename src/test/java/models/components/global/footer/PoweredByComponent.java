package models.components.global.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PoweredByComponent {

    private final WebDriver driver;
    private final By textSel = By.cssSelector(".footer-poweredby");
    private final By linkSel = By.cssSelector("");

    public PoweredByComponent(WebDriver driver) {
        this.driver = driver;
    }

    public String getText() {
        return this.driver.findElement(textSel).getText();
    }

    public String getLink() {
        return this.driver.findElement(linkSel).getAttribute("href");
    }

}
