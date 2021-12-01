package com.butbetter.applicationServices;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

public interface CalculatorService {

    //public float calculateVATofProduct(Product product, float percent);

    //public float calculateVATofProducts(List<Product> products, float percent);

    float calculateVATofPrice(float price, float percent) throws ResourceAccessException, HttpClientErrorException.BadRequest;
}
