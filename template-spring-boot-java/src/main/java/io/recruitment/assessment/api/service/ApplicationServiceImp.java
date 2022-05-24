package io.recruitment.assessment.api.service;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import io.recruitment.assessment.api.model.*;

@Component
public class ApplicationServiceImp implements ApplicationService{

    //Shopping cart that will hold products that a user has added to their cart
    private Map<Integer, ArrayList<Product>> shoppingCart;
    //News for the banner
    private String news;

    public ApplicationServiceImp() {
        shoppingCart = new HashMap<>();
        news = "Welcome to the online tool shop";
    }


    @Override   //Add product to a users cart
    public void addCart(int userID, Product tempProduct) {

        ArrayList<Product> productList = shoppingCart.get(userID);

        // If user doesn't have a cart
        if (productList == null) {  // Create new list of products then add it to a users shopping cart
            productList = new ArrayList<Product>();
            productList.add(tempProduct);
            shoppingCart.put(userID, productList);
        } else {                    //Add to existing shopping cart
            productList.add(tempProduct);
            shoppingCart.put(userID, productList);
        }

    }

    @Override   //Get a users cart
    public List<Product> getCart(int userID) {

        List<Product> productList = shoppingCart.get(userID);

        // If user doesn't have a cart
        if (productList == null) {
            return null;
        }
        return productList;

    }


    @Override   //Clear a users cart
    public boolean clearCart(int userID) {

        List<Product> productList = shoppingCart.get(userID);

        // If user doesn't have a cart
        if (productList == null) {
            return false;
        }

        //Remove the users cart
        shoppingCart.remove(userID);
        return true;
    }

    @Override   //remove a specific product from a users cart
    public boolean removeProduct(int userID, int productID) {

        ArrayList<Product> productList = shoppingCart.get(userID);

        // If user doesn't have a cart
        if (productList == null) {
            return false;
        }

        //Find the product in the cart
        for(int i = 0; i < productList.size(); i++)
        {
            //Current product being checked
            Product currProduct = productList.get(i);

            //If specific product is in the cart
            if(currProduct.getProductID() == productID){
                //Remove product form cart
                productList.remove(i);
                break; //Exit as only 1 should be removed at a time
            }else{     //Product not in cart
                return false;
            }
        }

        //Update cart to new values
        shoppingCart.put(userID, productList);
        return true;

    }

    @Override   //Update the news banner
    public void updateNews(String newNews){
        this.news = newNews;
    }

    @Override   //Get news banner
    public String getNews(){
        return news;
    }

    @Override   //Sort product list in ascending order
    public List<Product> sortListAsc(List<Product> productList){

        Collections.sort(productList, new priceComparatorAsc());
        return productList;

    }

    @Override   //Sort product list in ascending order
    public List<Product> sortListDesc(List<Product> productList){

        Collections.sort(productList, new priceComparatorDesc());
        return productList;

    }

}
