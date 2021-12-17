package com.butbetter.applicationservices;

import com.butbetter.applicationservices.model.CalcApiResponse;
import com.butbetter.applicationservices.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class Calculator implements CalculatorService {

    private String calcApiUrl ="https://caluationapi.herokuapp.com";
    private String calcApiPath = "/calc/v1/VAT?";
    private String calcApiUrlPrice = "price=";
    private String calcApiUrlPercent = "&percent=";

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    @Autowired
    private RestTemplate calcAPIRESTTemplate;

    public String getCalcApiUrl() {
        return calcApiUrl;
    }

    public String getCalcApiPath() {
        return calcApiPath;
    }

    public String getCalcApiUrlPrice() {
        return calcApiUrlPrice;
    }

    public String getCalcApiUrlPercent() {
        return calcApiUrlPercent;
    }

    public void setCalcApiUrl(String calcApiUrl) {
        log.info("calcApi url : " + calcApiUrl);
        this.calcApiUrl = calcApiUrl;
    }

    public void setCalcApiPath(String calcApiPath) {
        log.info("calcApi path: " + calcApiPath);
        this.calcApiPath = calcApiPath;
    }

    public void setCalcApiUrlPrice(String calcApiUrlPrice) {
        log.info("calcApi url price: " + calcApiUrlPrice);
        this.calcApiUrlPrice = calcApiUrlPrice;
    }

    public void setCalcApiUrlPercent(String calcApiUrlPercent) {
        log.info("calcApi url percent: " + calcApiUrlPercent);
        this.calcApiUrlPercent = calcApiUrlPercent;
    }


    @Override
    public float calculateVATofPrice(float price, float percent) throws ResourceAccessException, HttpClientErrorException.BadRequest {
        String restReq = this.calcApiUrl + this.calcApiPath + this.calcApiUrlPrice + price + this.calcApiUrlPercent + percent;
        log.info("accessing [" + restReq + "] to calc VAT of " + price + " with VAT(" + percent + "%)");
        CalcApiResponse calcApiResponse = calcAPIRESTTemplate.getForObject(restReq, CalcApiResponse.class);
        if(calcApiResponse == null) throw new IllegalStateException("Calc api response is null because spring boot didnt throw ResourcAccessException");
        return Float.parseFloat(calcApiResponse.getVatResult());
    }
}
