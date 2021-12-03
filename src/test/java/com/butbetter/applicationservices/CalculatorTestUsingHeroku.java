package com.butbetter.applicationservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorTestUsingHeroku {

    @Autowired
    private Calculator calculator;

    //integration Testing on heroku:

    //these tests assert to true if the heroku calc api is running
    @Test
    @DisplayName("test calc API on heroku with the values 100 10")
    void testCalcAPIReturnValueFor10percentFrom100(){
        assertEquals(110,calculator.calculateVATofPrice(100,10));
    }

    @Test
    @DisplayName("test calc API with the values -100 10")
    void testCalcAPIReturnForIllegalInputPrice(){
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(-100,10));
    }

    @Test
    @DisplayName("test calc API with the values 100 -10")
    void testCalcAPIReturnForIllegalInputPercent(){
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(100,-10));
    }

    //this test asserts to true if heroku calcApi is not running
    @Test
    @DisplayName("test a not running calc API with the values 100 10")
    void testCalcAPIReturnIfCalcAPINotAvailable(){
        assertThrows(ResourceAccessException.class, ()-> calculator.calculateVATofPrice(100,10));
    }


}