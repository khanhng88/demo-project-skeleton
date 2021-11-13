package models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompleteCheckOutPage {
    private WebDriver driver;
    private By pageTitleSel = By.className("page-title");

    public CompleteCheckOutPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("/checkout/completed/"));
    }

    public WebElement pageTitle () {
        return driver.findElement(pageTitleSel);
    }

    public CompletedCheckOutDataComp completedCheckOutDataComp () {
        return new CompletedCheckOutDataComp(driver);
    }

    public static class CompletedCheckOutDataComp {
        private WebDriver driver;
        private By orderDetailSel = By.cssSelector(".order-completed .details");
        private By orderDetailNumSel = By.cssSelector("li:first-child");
        private By orderDetailLinkSel = By.tagName("a");
        private By continueBtnSel = By.cssSelector(".order-completed input");

        public CompletedCheckOutDataComp(WebDriver driver) {
            this.driver = driver;
        }

        private WebElement orderDetail() {
            return driver.findElement(orderDetailSel);
        }

        public WebElement orderDetailLink() {
            return orderDetail().findElement(orderDetailLinkSel);
        }

        public WebElement continueBtn() {
            return driver.findElement(continueBtnSel);
        }

        public WebElement orderNumber() {
            return orderDetail().findElement(orderDetailNumSel);
        }

        public String orderNum() {
            return orderNumber().getText().split(":")[1].trim();
        }
    }
}
