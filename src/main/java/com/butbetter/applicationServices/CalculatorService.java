package com.butbetter.applicationServices;

public interface CalculatorService {

    //public float calculateVATofProduct(Product product, float percent);

    //public float calculateVATofProducts(List<Product> products, float percent);

    public float calculateVATofPrice(float price, float percent);
}
