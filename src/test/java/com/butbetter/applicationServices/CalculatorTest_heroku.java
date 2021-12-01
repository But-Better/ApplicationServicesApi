package com.butbetter.applicationServices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorTest_heroku {

    @Autowired
    private Calculator calculator;

    //integration Testing on heroku:

    //these tests assert to true if the heroku calc api is running
    @Test
    @DisplayName("test calc API on heroku with the values 100 10")
    void testCalcApiReturnValueFor10percentFrom100(){
        assertEquals(110,calculator.calculateVATofPrice(100,10));
    }

    @Test
    @DisplayName("test calc API with the values -100 10")
    void testCalcApiReturnForIllegalInputPrice(){
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(-100,10));
    }

    @Test
    @DisplayName("test calc API with the values 100 -10")
    void testCalcApiReturnForIllegalInputPercent(){
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calculator.calculateVATofPrice(100,-10));
    }

    //this test asserts to true if heroku calcApi is not running
    @Test
    @DisplayName("test a not running calc API with the values 100 10")
    void testCalcApiReturnIFCalcAPINotAvailable(){
        assertThrows(ResourceAccessException.class, ()-> calculator.calculateVATofPrice(100,10));
    }


}