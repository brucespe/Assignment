package io.recruitment.assessment.api.service;

import java.util.List;

import io.recruitment.assessment.api.model.*;

public interface ApplicationService{    

    default void addProduct( Product product){  }

    default Product getProduct(int productID){
        return getProduct(productID);
    }

    default void addCart(int userID, Product tempProduct){  }

    default List<Product> getCart(int userID) {
        return getCart(userID);
    }

    default boolean clearCart(int userID){
        return clearCart(userID);
    }

    default boolean removeProduct(int userID, int productID){
        return removeProduct(userID, productID);
    }

    default void updateNews(String newNews){  }

    default String getNews(){
        return getNews();
    }

    default List<Product> sortListAsc(List<Product> productList){
        return sortListAsc(productList);
    }

    default List<Product> sortListDesc(List<Product> productList){
        return sortListDesc(productList);
    }

}