package models.components.cart;

public class CartItemRowData {
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
