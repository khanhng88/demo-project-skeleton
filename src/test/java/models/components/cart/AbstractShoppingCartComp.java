package models.components.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractShoppingCartComp {
    private final WebDriver driver;
    private final By itemTotalPriceSel = By.className("product-subtotal");
    private By cartItemListSel = By.className("cart-item-row");
    private By productImgSrcSel = By.cssSelector(".product-picture >img");
    private By productNameSel = By.className("product-name");
    private By productAttributesSel = By.className("attributes");
    private By productEditLinkSel = By.className("edit-item");
    private By productPriceSel = By.className("unit-price");
    private By productQuantitySel = By.className("qty-input");
    private By productSubTotalSel = By.className("subtotal");

    protected abstract By productQuantitySel();

    protected abstract Boolean isSummaryCartComp();

    public AbstractShoppingCartComp(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get current total price")
    public double itemTotalPrice(){
        String itemTotalPriceStr = driver.findElement(itemTotalPriceSel).getText();
        return Double.parseDouble(itemTotalPriceStr);
    }

    public List<CartItemRowData> cartItemRowDataList() {
        List<CartItemRowData> cartItemRowDataList = new ArrayList<>();
        List<WebElement> cartItemList = driver.findElements(cartItemListSel);
        for(WebElement cartItem : cartItemList) {
            String imgSrc = cartItem.findElement(productImgSrcSel).getAttribute("src");
            String productName = cartItem.findElement(productNameSel).getText();
            String productLink = cartItem.findElement(productNameSel).getAttribute("href");

            List<WebElement> productAttributes = cartItem.findElements(productAttributesSel);
            String attributes = productAttributes.isEmpty() ? null : productAttributes.get(0).getText();

            List<WebElement> productEditLink = cartItem.findElements(productEditLinkSel);
            String editLink = productEditLink.isEmpty() ? null : productEditLink.get(0).getAttribute("href");

            double productPrice = Double.parseDouble(cartItem.findElement(productPriceSel).getText());
            int productQuantity = isSummaryCartComp()? Integer.parseInt(cartItem.findElement(productQuantitySel()).getText())
                    :Integer.parseInt(cartItem.findElement(productQuantitySel()).getAttribute("value"));
            double productSubTotal = Double.parseDouble(cartItem.findElement(productSubTotalSel).getText());

            CartItemRowData cartItemRowData = new CartItemRowData(imgSrc, productName, attributes, productLink,
                    editLink, productPrice, productQuantity, productSubTotal);
            cartItemRowDataList.add(cartItemRowData);
        }
        return cartItemRowDataList;
    }

    public static class CartItemRowData {
        private String imgSrc;
        private String productName;
        private String productAttributes;
        private String productLink;
        private String productEditLink;
        private double price;
        private int quantity;
        private double subTotal;

        public CartItemRowData(String imgSrc, String productName, String productAttributes, String productLink,
                               String productEditLink, double price, int quantity, double subTotal) {
            this.imgSrc = imgSrc;
            this.productName = productName;
            this.productAttributes = productAttributes;
            this.productLink = productLink;
            this.productEditLink = productEditLink;
            this.price = price;
            this.quantity = quantity;
            this.subTotal = subTotal;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductAttributes() {
            return productAttributes;
        }

        public String getProductLink() {
            return productLink;
        }

        public String getProductEditLink() {
            return productEditLink;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getSubTotal() {
            return subTotal;
        }
    }
}
