package io.recruitment.assessment.api.repository;

import java.util.List;

import io.recruitment.assessment.api.model.*;

public interface ApplicationRepository {

    default void createAccount(User currUser) { }

    default List<Product> getAllProducts(){
        return getAllProducts();
    }

    default List<User> getAllUsers(){
        return getAllUsers();
    }

    default User getUser(int userID){
        return getUser(userID);
    }

    default Product getProduct(int productID){
        return getProduct(productID);
    }

    default boolean purchaseCart(int userID, List<Product> cart) {
        return purchaseCart(userID, cart);
    }

    default List<Order> getOrders(int userID) {
        return getOrders(userID);
    }

    default boolean addProduct(Product newProduct){ return addProduct(newProduct); }

    default boolean updateProduct(Product newProduct) { return updateProduct(newProduct); }

    default boolean deleteProduct(int productID) { return deleteProduct(productID); }

    default List<Product> getProductsAsc() {
        return getProductsAsc();
    }

    default List<Product> getProductsDesc() {
        return getProductsDesc();
    }

    default Product getProductsByName(String name) {
        return getProductsByName(name);
    }
    
}
