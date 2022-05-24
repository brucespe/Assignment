package io.recruitment.assessment.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.recruitment.assessment.api.model.*;
import io.recruitment.assessment.api.repository.ApplicationRepository;
import io.recruitment.assessment.api.service.*;

@RestController
@RequestMapping("toolShop")
public class ApplicationController {

    private ApplicationService as;
    private ApplicationRepository ar;

    public ApplicationController(ApplicationService as, ApplicationRepository ar) {

        // Service to handle service interations
        this.as = as;
        // Repository to handle database interaction
        this.ar = ar;
    }

    // Returns a list of users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        // Get list of all users from repository
        List<User> userAccounts = ar.getAllUsers();

        // No accounts found
        if (userAccounts == null) {
            System.out.println("getAllUsers - No users have been found\n");
            return new ResponseEntity<List<User>>((userAccounts), HttpStatus.NOT_FOUND);
        }

        // Printing info to help see what is happening
        for (int i = 0; i < userAccounts.size(); i++) {
            User currUser = userAccounts.get(i);
            System.out.println(currUser.toString());
        }
        System.out.println();

        // List of users that is returned to frontend
        return new ResponseEntity<List<User>>((userAccounts), HttpStatus.OK);
    }

    // Getting a user by ID
    @GetMapping("/getUser/{userID}")
    public ResponseEntity<User> getUser(@PathVariable int userID) {

        User currUser = ar.getUser(userID);

        // User doesn't exist on db
        if (currUser == null) {
            System.out.println("getUser - User with this ID cannot be found\n");
            return new ResponseEntity<User>((currUser), HttpStatus.NOT_FOUND);
        }

        System.out.println(currUser.toString() + "\n");
        return new ResponseEntity<User>((currUser), HttpStatus.OK);
    }

    // Returning a list of user accounts from the database
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {

        // Get list of all products in the database
        List<Product> productCatalogue = ar.getAllProducts();

        // No products could be found
        if (productCatalogue == null) {
            System.out.println("getAllProducts - no products can be found\n");
            return new ResponseEntity<List<Product>>((productCatalogue), HttpStatus.NOT_FOUND);
        }

        // Display all products
        for (int i = 0; i < productCatalogue.size(); i++) {
            Product curr = productCatalogue.get(i);
            System.out.println(curr.toString());
        }

        System.out.println();
        return new ResponseEntity<List<Product>>((productCatalogue), HttpStatus.OK);

    }

    // Get a product by its ID
    @GetMapping("/getProduct/{productID}")
    public ResponseEntity<Product> getProduct(@PathVariable int productID) {

        // Get the product from db
        Product tempProd = ar.getProduct(productID);

        // Product doesnt exist, returns null
        if (tempProd == null) {
            System.out.println("getProduct - This product does not exist");
            return new ResponseEntity<Product>((tempProd), HttpStatus.NOT_FOUND);
        }

        System.out.println("getProduct - " + tempProd.toString());
        return new ResponseEntity<Product>((tempProd), HttpStatus.OK);

    }

    // Add a product to a users cart
    @PutMapping("/addCart/{userID}/{productID}")
    public ResponseEntity<Void> addCart(@PathVariable int userID, @PathVariable int productID) {

        // First check if the user has an account and the product exists
        Product tempProd = ar.getProduct(productID);
        User tempUser = ar.getUser(userID);

        if (tempProd == null) { // Product does not exist
            System.out.println("addCart - Product with this ID does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else if (tempUser == null) { // User does not exist
            System.out.println("addCart - User with this ID does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        System.out.println(
                "addCart - Adding ID: " + tempProd.getProductID() + " - " + tempProd.getProductName() + " to cart");
        // Add user to the cart stored in the application service
        as.addCart(userID, tempProd);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    // Return a users cart
    @GetMapping("/viewCart/{userID}")
    public ResponseEntity<List<Product>> viewCart(@PathVariable int userID) {

        // Get the user and their cart
        User currUser = ar.getUser(userID);
        List<Product> productList = as.getCart(userID);

        // User doesn't exist
        if (currUser == null) {
            System.out.println("viewCart - This user does not exist");
            return new ResponseEntity<List<Product>>((productList), HttpStatus.NOT_FOUND);
        }

        // User doesn't have anything in the cart
        if (productList == null) {
            System.out.println("viewCart - This users cart is empty");
            return new ResponseEntity<List<Product>>((productList), HttpStatus.NOT_FOUND);
        }

        // Print all the items in the cart
        for (int i = 0; i < productList.size(); i++) {
            Product curr = productList.get(i);
            System.out.println(curr.toString());
        }

        return new ResponseEntity<List<Product>>((productList), HttpStatus.OK);
    }

    // Get the total price of all the items in the cart
    @GetMapping("/cartPrice/{userID}")
    public ResponseEntity<Integer> cartPrice(@PathVariable int userID) {

        // Get a users cart
        List<Product> productList = as.getCart(userID);
        User currUser = ar.getUser(userID);
        int totalCost = 0;

        // User doesnt exist
        if (currUser == null) {
            System.out.println("cartPrice - This user does not exist");
            return new ResponseEntity<Integer>((totalCost), HttpStatus.NOT_FOUND);
        }

        // User doesn't have anything in the cart
        if (productList == null) {
            System.out.println("cartPrice - This users cart is empty");
            return new ResponseEntity<Integer>((totalCost), HttpStatus.NOT_FOUND);
        }

        // Calculate the total cost
        for (Product currProduct : productList) {
            totalCost += currProduct.getProductPrice();
        }

        // Print total cost
        System.out.println("cartPrice - Total cost - " + totalCost + "\n\n");
        return new ResponseEntity<Integer>((totalCost), HttpStatus.OK);
    }

    // Delete all the items in a users cart
    @DeleteMapping("/clearCart/{userID}")
    public ResponseEntity<Void> clearCart(@PathVariable int userID) {

        User currUser = ar.getUser(userID);

        // User doesn't exist
        if (currUser == null) {
            System.out.println("clearCart - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        // boolean if clear was successful (false if cart doesnt exist)
        boolean cleared = as.clearCart(userID);
        if (!cleared) { // Inform users cart is empty
            System.out.println("clearCart - Shopping cart for this user is empty");
            // Can return ok as either way shopping cart is cleared
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        System.out.println("clearCart - Shopping cart has been cleared");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // Delete 1 specific item from a users cart
    @DeleteMapping("/removeProductCart/{userID}/{productID}")
    public ResponseEntity<Void> removeProductCart(@PathVariable int userID, @PathVariable int productID) {

        User currUser = ar.getUser(userID);
        Product currProduct = ar.getProduct(productID);

        if (currUser == null) {
            System.out.println("removeProductCart - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        // Product doesnt exist
        if (currProduct == null) {
            System.out.println("removeProductCart - This product does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        // boolean if clear was successful (false if cart doesnt exist, or product wasnt
        // in cart)
        // If two of the same item in cart, it will only delete 1
        boolean removed = as.removeProduct(userID, productID);
        if (!removed) {
            System.out.println("removeProductCart - this product is not in user cart");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        System.out.println(
                "removeProductCart - 1 Product: " + currProduct.getProductName() + " has been removed from cart");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // Purchase the contents in the users cart
    @PostMapping("/purchase/{userID}")
    public ResponseEntity<Void> purchaseCart(@PathVariable int userID) {

        int totalCost = 0;
        List<Product> productList = as.getCart(userID);

        if (productList == null) {
            System.out.println("purchase - no items in cart");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        // Boolean if purchase was successful
        boolean purchase = ar.purchaseCart(userID, productList);

        if (!purchase) { // Unable to purchase
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        // Find the total cost
        for (Product currProduct : productList) {
            totalCost += currProduct.getProductPrice();
        }

        System.out.println("purchase - Items in cart have been purchased for £" + totalCost);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // Get a users orders from the db
    @GetMapping("/getOrders/{userID}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable int userID) {

        User currUser = ar.getUser(userID);
        int totalPrice = 0;

        if (currUser == null) {
            System.out.println("getOrders - This user does not exist");
            return new ResponseEntity<List<Order>>(HttpStatus.NOT_FOUND);
        }

        // Get list of users previous orders
        List<Order> orderList = ar.getOrders(userID);
        // User does not have any previous orders
        if (orderList == null) {
            System.out.println("getOrders - This user does not have existing orders");
            return new ResponseEntity<List<Order>>((orderList), HttpStatus.NOT_FOUND);
        }

        // Print the order details, along with the item in the order
        for (int i = 0; i < orderList.size(); i++) {
            Order currOrder = orderList.get(i);
            System.out.println(currOrder.toString());
            Product currProduct = ar.getProduct(currOrder.getProductID());
            System.out.println("Containing: " + currProduct.toString());
            totalPrice += currProduct.getProductPrice();

        }

        // Print the total cost of all orders
        System.out.println("getOrders - Total amount spent: " + totalPrice + "\n");
        return new ResponseEntity<List<Order>>((orderList), HttpStatus.OK);
    }

    // Allow admins to update the news for the banner
    @PutMapping("/updateNews/{userID}")
    public ResponseEntity<Void> updateNews(@PathVariable int userID, @RequestBody String news) {

        User user = ar.getUser(userID);

        if (user == null) {
            System.out.println("updateNews - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        // Checking the users accessLevel as only admins may update the news
        String userAccLevel = user.getAccessLevel();

        if (!userAccLevel.equals("Admin")) { // User is not an admin
            System.out.println("Only admins may update the news banner");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        // Update the news banner
        as.updateNews(news);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    // Get the current news banner
    @GetMapping("/getNews")
    public ResponseEntity<String> getNews() {

        String news = as.getNews();
        System.out.println("getNews - " + news + "\n");
        return new ResponseEntity<String>((news), HttpStatus.OK);

    }

    // Add a product to the database if the user is an admin
    @PostMapping("/addProduct/{userID}")
    public ResponseEntity<Void> addProduct(@PathVariable int userID, @RequestBody Product brokenewProduct) {

        // Unable to pass object corrently through curl, can instead test this method by
        // manipulating a product object here
        Product newProduct = new Product(43, "example new tool", "new tool desc", 20, 5);
        User user = ar.getUser(userID);

        if (user == null) {
            System.out.println("addProduct - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        String userAccLevel = user.getAccessLevel();

        if (!userAccLevel.equals("Admin")) { // User is not an admin
            System.out.println("addProduct - Only admins may add new products");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        // Try and find an existing object with the same ID, as productID's must be
        // unique
        Product existingProduct = ar.getProduct(newProduct.getProductID());

        if (existingProduct != null) { // Product with this ID found
            System.out.println("addProduct - Product with this ID already exists");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        // Add product to the db
        ar.addProduct(newProduct);
        System.out.println(newProduct.toString() + " has been added to the catalogue");
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    // Update an existing product
    @PutMapping("/updateProduct/{userID}")
    public ResponseEntity<Void> updateProduct(@PathVariable int userID, @RequestBody Product brokenewProduct) {

        // Same issue as previous method
        Product newProduct = new Product(43, "example new tool updated", "new tool description", 28, 8);
        User user = ar.getUser(userID);

        if (user == null) {
            System.out.println("updateProduct - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        String userAccLevel = user.getAccessLevel();

        if (!userAccLevel.equals("Admin")) {
            System.out.println("updateProduct - Only admins may update products");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        // Again check if product exists
        Product existingProduct = ar.getProduct(newProduct.getProductID());

        if (existingProduct == null) { // Product doesnt exist, so can insert to db
            ar.addProduct(newProduct);
            System.out.println(newProduct.toString() + " has been added to the catalogue");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        // If the product update was successful
        boolean updated = ar.updateProduct(newProduct);

        if (updated == true) { // Product has been updated
            System.out.println(newProduct.toString() + " has been updated in the catalogue");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else { // Product could not be updated
            System.out.println(newProduct.toString() + " could not be updated");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

    }

    // Delete a product from the db, can only be done if user is admin
    @DeleteMapping("/deleteProduct/{userID}/{productID}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int userID, @PathVariable int productID) {

        User user = ar.getUser(userID);

        if (user == null) {
            System.out.println("deleteProduct - This user does not exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        String userAccLevel = user.getAccessLevel();

        if (!userAccLevel.equals("Admin")) {
            System.out.println("deleteProduct - Only admins may delete products");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        Product existingProduct = ar.getProduct(productID);

        if (existingProduct == null) { // Product doesnt exist so cannot be delted
            System.out.println("deleteProduct - This product doesn't exist, cannot delete");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        // If product was delted
        boolean deleted = ar.deleteProduct(productID);

        if (deleted == true) {
            System.out.println(existingProduct.toString() + " has been delete from the catalogue");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            System.out.println(existingProduct.toString() + " could not be deleted");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/filterAsc")
    public ResponseEntity<List<Product>> getProductsAsc() {

        List<Product> productList = ar.getAllProducts();
        // Pass list to application service, will return a sorted list
        List<Product> sortedListAsc = as.sortListAsc(productList);

        // Print the order details, along with the item in the order
        for (int i = 0; i < sortedListAsc.size(); i++) {
            Product currProduct = sortedListAsc.get(i);
            System.out.println(currProduct.toString());

        }

        return new ResponseEntity<List<Product>>((sortedListAsc), HttpStatus.OK);

    }

    @GetMapping("/filterDesc")
    public ResponseEntity<List<Product>> getProductsDesc() {

        List<Product> productList = ar.getAllProducts();
        // Pass list to application service, will return a sorted list
        List<Product> sortedListDesc = as.sortListDesc(productList);

        // Print the order details, along with the item in the order
        for (int i = 0; i < sortedListDesc.size(); i++) {
            Product currProduct = sortedListDesc.get(i);
            System.out.println(currProduct.toString());

        }

        return new ResponseEntity<List<Product>>((sortedListDesc), HttpStatus.OK);

    }

    @GetMapping("/getProductByName")
    public ResponseEntity<Product> getProductByName(String name) {

        Product currProduct = ar.getProductsByName(name);

        if(currProduct == null){
            System.out.println("getProductByName - This product could not be found");
            return new ResponseEntity<Product>((currProduct), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Product>((currProduct), HttpStatus.OK);

    }

}
