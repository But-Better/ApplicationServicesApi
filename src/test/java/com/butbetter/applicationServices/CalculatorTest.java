package com.butbetter.applicationServices;

import com.butbetter.applicationServices.model.CalcApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorTest {

    @Autowired
    private Calculator calculator;

    //integration Testing on localhost:

    @Test
    void testCalcApiReturnValueFor10percentFrom100(){
        calculator.setCALC_API_URL_START("http://localhost:8080/calc/v1/VAT?price=");
        assertEquals(110,calculator.calculateVATofPrice(100,10));
    }

    //FAILS HOW DO WE HANDLE BAD REQUESTS?
    @Test
    void testCalcApiReturnForIllegalInput(){
        calculator.setCALC_API_URL_START("http://localhost:8080/calc/v1/VAT?price=");
        assertEquals(110,calculator.calculateVATofPrice(-100,10));
    }


}