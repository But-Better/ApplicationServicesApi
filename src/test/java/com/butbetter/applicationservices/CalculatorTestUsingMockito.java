package com.butbetter.applicationservices;

import com.butbetter.applicationservices.caluapi.model.VAT;
import com.butbetter.applicationservices.caluapi.service.Calculator;
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
public class CalculatorTestUsingMockito {

    @Mock
    private RestTemplate calcRestApiTemplateMock;

    @InjectMocks
    private Calculator calcConnected = new Calculator();

    @Autowired
    private Calculator calcNoConnection;

    @Test
    @DisplayName("test calc API by mocking restTemplate with the values 100 10")
    void testCalcAPIReturnValueFor10percentFrom100(){
        VAT fakeResponse = new VAT("110");
        String reqURL = calcConnected.getCalcApiUrl() + calcConnected.getCalcApiPath() + calcConnected.getCalcApiUrlPrice() +  "100.0" + calcConnected.getCalcApiUrlPercent() + "10.0";
        Mockito.when(calcRestApiTemplateMock.getForObject(reqURL, VAT.class)).thenReturn(fakeResponse);
        assertEquals(110, calcConnected.calculateVATofPrice(100,10));
    }

    @Disabled
    @Test
    void testCalcAPIReturnForIllegalInputPrice(){
        //write a test that provokes a 400 type response -> spring boot throw bad request exc -> assert for this exception
        //problem i cannot mock a 400 type response using restTemplate.getForObject
        VAT fakeResponse = new VAT("110");
        String reqURL = calcConnected.getCalcApiUrl() + calcConnected.getCalcApiPath() + calcConnected.getCalcApiUrlPrice() +  "-100.0" + calcConnected.getCalcApiUrlPercent() + "10.0";
        Mockito.when(calcRestApiTemplateMock.getForObject(reqURL, VAT.class)).thenReturn(fakeResponse);
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calcConnected.calculateVATofPrice(-100,10));
    }

    @Disabled
    @Test
    void testCalcAPIReturnForIllegalInputPercent(){
        //write a test that provokes a 400 type response -> spring boot throw bad request exc -> assert for this exception
        //problem i cannot mock a 400 type response using restTemplate.getForObject
        VAT fakeResponse = new VAT("110");
        String reqURL = calcConnected.getCalcApiUrl() + calcConnected.getCalcApiPath() + calcConnected.getCalcApiUrlPrice() +  "100.0" + calcConnected.getCalcApiUrlPercent() + "-10.0";
        Mockito.when(calcRestApiTemplateMock.getForObject(reqURL, VAT.class)).thenReturn(fakeResponse);
        assertThrows(HttpClientErrorException.BadRequest.class, ()-> calcConnected.calculateVATofPrice(100,-10));
    }

    @Test
    @DisplayName("test not running calc API by using non existend url")
    void testCalcAPIReturnIfCalcAPINotAvailable(){
        String url = "https://thisUrl345345435";
        String urlPath = "/wrong/path/alsdgiojangasl/VAT?";
        String urlPrice = "price=";
        String urlPercent = "&percent=";
        calcNoConnection.setCalcApiUrl(url);
        calcNoConnection.setCalcApiPath(urlPath);
        calcNoConnection.setCalcApiUrlPrice(urlPrice);
        calcNoConnection.setCalcApiUrlPercent(urlPercent);
        assertThrows(ResourceAccessException.class, ()-> calcNoConnection.calculateVATofPrice(100,10));
    }

}
