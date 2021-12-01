package com.butbetter.applicationServices;

import com.butbetter.applicationServices.model.CalcApiResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CalculatorTest_mockito {

    @Mock
    private RestTemplate calcRestApiTemplate_Mock;

    @InjectMocks
    private Calculator calcConnected = new Calculator();

    @Autowired
    private Calculator calcNoConnection;

    @Test
    @DisplayName("test calc API by mocking restTemplate with the values 100 10")
    void testCalcApiReturnValueFor10percentFrom100(){
        CalcApiResponse fakeResponse = new CalcApiResponse("110");
        String reqURL = calcConnected.getCalcApiUrlStart() + "100.0" + calcConnected.getCalcApiUrlEnd() + "10.0";
        Mockito.when(calcRestApiTemplate_Mock.getForObject(reqURL, CalcApiResponse.class)).thenReturn(fakeResponse);
        assertEquals(110, calcConnected.calculateVATofPrice(100,10));
    }

    @Disabled
    @Test
    void testCalcApiReturnForIllegalInputPrice(){
        //write a test that provokes a 400 type response -> spring boot throw bad request exc -> assert for this exception
        //problem i cannot mock a 400 type response using restTemplate.getForObject
        CalcApiResponse fakeResponse = new CalcApiResponse("110");
        String reqURL = calcConnected.getCalcApiUrlStart() + "-100.0" + calcConnected.getCalcApiUrlEnd() + "10.0";
        Mockito.when(calcRestApiTemplate_Mock.getForObject(reqURL, CalcApiResponse.class)).thenReturn(fakeResponse);
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calcConnected.calculateVATofPrice(-100,10));
    }

    @Disabled
    @Test
    void testCalcApiReturnForIllegalInputPercent(){
        //write a test that provokes a 400 type response -> spring boot throw bad request exc -> assert for this exception
        //problem i cannot mock a 400 type response using restTemplate.getForObject
        CalcApiResponse fakeResponse = new CalcApiResponse("110");
        String reqURL = calcConnected.getCalcApiUrlStart() + "-100.0" + calcConnected.getCalcApiUrlEnd() + "-10.0";
        Mockito.when(calcRestApiTemplate_Mock.getForObject(reqURL, CalcApiResponse.class)).thenReturn(fakeResponse);
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calcConnected.calculateVATofPrice(100,-10));
    }

    @Test
    @DisplayName("test not running calc API by using non existend url")
    void testCalcApiReturnIFCalcAPINotAvailable(){
        String urlStart = "https://thisUrl";
        String urlEnd = "cannotbereachedbecauseitdoesntexistandthereforethereshouldbea404comingin.com/";
        calcNoConnection.setCalcApiUrlStart(urlStart);
        calcNoConnection.setCalcApiUrlEnd(urlEnd);
        assertThrows(ResourceAccessException.class, ()-> calcNoConnection.calculateVATofPrice(100,10));
    }

}
