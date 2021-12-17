package com.butbetter.applicationservices.calculator;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

public interface CalculatorService {

    float calculateVATofPrice(float price, float percent) throws ResourceAccessException, HttpClientErrorException.BadRequest;
}
