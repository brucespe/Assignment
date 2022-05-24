package io.recruitment.assessment.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.recruitment.assessment.api.model.*;

//Repository to handle external communication (SQL database)
@Repository
public class ApplicationRepositoryImpl implements ApplicationRepository {

    @Autowired
    private JdbcTemplate externalDB;       //Database linked in resource file

    @Override   //Get all user accounts
    public List<User> getAllUsers() {

        String sql = "SELECT * FROM users";
        List<User> users = null;
        users = externalDB.query(sql, new UserMapper());
        return users;

    }

    @Override   //Get a user by their ID
    public User getUser(int userID) {

        String sql = "SELECT * FROM users WHERE UserID = ?";
        User user = null;

        try{//Query the database, use a mapper to map values returned from db
            user = externalDB.queryForObject(sql, new UserMapper(), userID);
        }catch(Exception e){    //User not found
            return null;
        }

        return user;

    }

    @Override   //Get list of all products
    public List<Product> getAllProducts() {

        String sql = "SELECT * FROM catalogue";
        List<Product> products = externalDB.query(sql, new ProductMapper());
        return products;

    }

    @Override   //Get an object by its ID
    public Product getProduct(int productID) {

        String sql = "SELECT * FROM catalogue WHERE ProductID = ?";
        Product product = null;

        try {
            product = externalDB.queryForObject(sql, new ProductMapper(), productID);

        } catch (Exception e) { } // Product remains null, error is handled after returning

        return product;
    }

    @Override   //Purchase the contents of users cart, cart list is passed
    public boolean purchaseCart(int userID, List<Product> cart) {

        //Creating an order for every product, stored userID along with productID
        String sql_purchase = "INSERT INTO orders (orderID, userID, productID) VALUES (?, ?, ?)";

        //For every product in the cart
        for (Product currProduct : cart) {
            //Create a randome orderID for every order
            int randOrderID = (int) (Math.random() * (10000 - 100) + 100);
            int productID = currProduct.getProductID();
            try {
                //Add order to order table
                externalDB.update(sql_purchase, randOrderID, userID, productID);

            } catch (Exception e) { return false;   } // Purchase unsucessful
        }

        //Order successful
        return true;
    }

    @Override   //Get all of a users orders
    public List<Order> getOrders(int userID) {

        String sql_orders = "SELECT * FROM orders WHERE userID = ?";
        List<Order> orders = new ArrayList<Order>();
        //For handling the rows that are returned
        List<Map<String, Object>> rows = null;

        try {
            //Gets a list of all the users orders
            rows = externalDB.queryForList(sql_orders, userID);
        } catch (Exception e) { } // No orders, will return null

        //For every row
        for (Map<String, Object> row : rows) {
            //Create order object of values that are returned
            Order order = new Order();
            order.setOrderID(Integer.parseInt(String.valueOf(row.get("orderID"))));
            order.setUserID(Integer.parseInt(String.valueOf(row.get("userID"))));
            order.setProductID(Integer.parseInt(String.valueOf(row.get("productID"))));
            orders.add(order);
        }

        //Returns the list of users orders
        return orders;
    }

    @Override   //Add a new product to the products table
    public boolean addProduct(Product newProduct) {

        //Get all new products information
        int prodID = newProduct.getProductID();
        String prodName = newProduct.getProductName();
        String prodDesc = newProduct.getProductDesc();
        int prodPrice = newProduct.getProductPrice();
        int setProductDiscount = newProduct.getProductDiscount();
        boolean Successful = false;

        //SQL to try add new product
        String sql_addProduct = "INSERT INTO catalogue (ProductID, Name, Description, Price, Discount) VALUES (?, ?, ?, ?, ?)";

        try {
            externalDB.update(sql_addProduct, prodID, prodName, prodDesc, prodPrice, setProductDiscount);
            Successful = true;
        } catch (Exception e) { } //Error adding new object, return false

        return Successful;
    }


    @Override   //Update an existing product
    public boolean updateProduct(Product newProduct) {

        int prodID = newProduct.getProductID();
        String new_prodName = newProduct.getProductName();
        String new_prodDesc = newProduct.getProductDesc();
        int new_prodPrice = newProduct.getProductPrice();
        int new_setProductDiscount = newProduct.getProductDiscount();
        boolean Successful = false;

        //Query to update an existing product
        String sql_updateProduct = "UPDATE catalogue SET Name = ?, Description = ?, Price = ?, Discount = ?  WHERE ProductID = ?";

        try {
            externalDB.update(sql_updateProduct, new_prodName, new_prodDesc, new_prodPrice, new_setProductDiscount, prodID);
            Successful = true;
        } catch (Exception e) { } //Product could not be updated

        return Successful;

    }

    @Override   //Delete a product from db
    public boolean deleteProduct(int productID) {

        boolean Successful = false;
        String sql_deleteProduct = "DELETE from catalogue WHERE ProductID = ?";

        try {
            externalDB.update(sql_deleteProduct, productID);
            Successful = true;
        } catch (Exception e) { } //Cannot delete product

        return Successful;
    }

    //Filter product by name
    @Override
    public Product getProductsByName(String name) {

        String sql = "SELECT * FROM catalogue WHERE Name = ?";
        Product product = null;

        try {
            product = externalDB.queryForObject(sql, new ProductMapper(), name);

        } catch (Exception e) { } // Product remains null, error is handled after returning

        return product;
    }

}
