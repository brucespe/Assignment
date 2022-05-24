package io.recruitment.assessment.api.service;

import java.util.Comparator;
import io.recruitment.assessment.api.model.Product;

public class priceComparatorDesc implements Comparator<Product> {

    @Override   //For comparing the price values
    public int compare(Product p1, Product p2) {
        return p2.getProductPrice() < p1.getProductPrice() ? -1 : p1.getProductPrice() == p2.getProductPrice() ? 0 : 1;
    }
}
