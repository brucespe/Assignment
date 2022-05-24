package io.recruitment.assessment.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "catalogue")
public class Product implements Serializable, Comparable<Product>  {

    @Id // Indicates its a primary key
    private int productID;              //Id of the product
    private String name;                //Name of the product
    private String description;         
    private int price;                  //Integer price of product
    private int discount;               //Money subtracted from previous price

    public Product() {
    } 

    public Product(int productID, String name, String description, int price, int discount) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    // Copy constructor
    public Product(Product that) {
        this.productID = that.productID;
        this.name = that.name;
        this.description = that.description;
        this.price = that.price;
        this.discount = that.discount;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String productName) {
        this.name = productName;
    }

    public String getProductDesc() {
        return description;
    }

    public void setProductDesc(String productDesc) {
        this.description = productDesc;
    }


    public int getProductPrice() {
        return price;
    }

    public void setProductPrice(int productPrice) {
        this.price = productPrice;
    }

    public int getProductDiscount() {
        return discount;
    }

    public void setProductDiscount(int productDiscount) {
        this.discount = productDiscount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID:\"" + productID + "\"," +
                "Name:\"" + name + "\"," +
                "Description:\"" + description + "\"," +
                "Price:\"" + price + "\"," +
                "Discount:\"" + discount + "\"}";
    }

    @Override
    public int compareTo(Product o) {
        return this.price - o.price;
    }

}
