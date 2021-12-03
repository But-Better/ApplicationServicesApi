package com.butbetter.applicationServices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorTestUsingLocalhost {

    @Autowired
    private Calculator calculator;

    //integration Testing on localhost:

    //these tests assert to true if the localhost calc api is running
    @Test
    @DisplayName("test calc API with the values 100 10")
    void testCalcApiReturnValueFor10percentFrom100(){
        calculator.setCalcApiUrl("http://localhost:8080");
        assertEquals(110,calculator.calculateVATofPrice(100,10));
    }

    @Test
    @DisplayName("test calc API with the values -100 10")
    void testCalcApiReturnForIllegalInputPrice(){
        calculator.setCalcApiUrl("http://localhost:8080");
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(-100,10));
    }

    @Test
    @DisplayName("test calc API with the values 100 -10")
    void testCalcApiReturnForIllegalInputPercent(){
        calculator.setCalcApiUrl("http://localhost:8080");
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(100,-10));
    }

    //this test asserts to true if localhost calcApi not running
    @Test
    @DisplayName("test not running calc API with the values 100 10")
    void testCalcApiReturnIFCalcAPINotAvailable(){
        calculator.setCalcApiUrl("http://localhost:8080");
        assertThrows(ResourceAccessException.class, ()-> calculator.calculateVATofPrice(100,10));
    }


}