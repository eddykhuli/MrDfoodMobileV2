package lagecy.live.desh.com.mrdfoodmobilev2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/23.
 */

public class OrderInfo {

    private String orderId;
    private long userId;
    private double subTotal;
    private double total;
    private String status;
    private String collectionType;
    private List<Product> cartItems =new ArrayList<>();
    private String restaurantName;
    private String orderDate;
    private String deliveryAddress;
    private String contactno;
    private String emailAddress;

    public OrderInfo( ) {
    }

    public OrderInfo(String orderId, long userId, double subTotal, double total, String status, String collectionType, List<Product> cartItems, String restaurantName, String orderDate, String deliveryAddress, String contactno, String emailAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.subTotal = subTotal;
        this.total = total;
        this.status = status;
        this.collectionType = collectionType;
        this.cartItems = cartItems;
        this.restaurantName = restaurantName;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.contactno = contactno;
        this.emailAddress = emailAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
