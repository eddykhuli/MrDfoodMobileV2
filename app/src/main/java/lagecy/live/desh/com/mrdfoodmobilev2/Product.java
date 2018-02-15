package lagecy.live.desh.com.mrdfoodmobilev2;

/**
 * Created by MANDELACOMP7 on 2018/01/11.
 */

class Product {

    private Long id;
    private String productName;
    private String category;
    private double price;
    private String description;
    private int quantity;

    public Product(){

    }

    public Product(Long id, String productName, String category, double price, String description, int quantity) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
