package io.recruitment.assessment.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id // Indicates its a primary key
    private int orderID;        //Id of an order
    private int userID;         //The userID that purchased the products
    private int productID;      //The product that was purchased

    //Constructors
    public Order() {
    } 

    public Order(int orderID, int userID, int productID) {
        this.orderID = orderID;
        this.userID = userID;
        this.productID = productID;
    }

    public Order(Order that) {
        this.orderID = that.orderID;
        this.userID = that.userID;
        this.productID = that.productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }   
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }   

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }   

    @Override
    public String toString() {
        return "Order{" +
                "orderID:\"" + orderID + "\"," +
                "userID:\"" + userID + "\"," +
                "productID:\"" + productID + "\"}";
    }

}
