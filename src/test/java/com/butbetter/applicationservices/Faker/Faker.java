package com.butbetter.applicationservices.Faker;

public class Faker {

    private WrongProduct wrongProduct;
    private ProductFaker productFaker;

    public Faker() {
        this.wrongProduct = new WrongProduct();
        this.productFaker = new ProductFaker();
    }

    public WrongProduct getWrongProduct() {
        return wrongProduct;
    }

    public ProductFaker getProductFaker() {
        return productFaker;
    }
}
