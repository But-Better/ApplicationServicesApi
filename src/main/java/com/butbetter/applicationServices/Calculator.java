package com.butbetter.applicationServices;

import com.butbetter.applicationServices.model.CalcApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class Calculator implements CalculatorService {

    private String calcApiUrlStart = "https://caluationapi.herokuapp.com/calc/v1/VAT?price=";
    private String calcApiUrlEnd = "&percent=";

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    public String getCalcApiUrlStart() {
        log.info("urlStart : " + calcApiUrlStart);
        return this.calcApiUrlStart;
    }

    public String getCalcApiUrlEnd() {
        log.info("urlEnd : " + calcApiUrlEnd);
        return calcApiUrlEnd;
    }

    public void setCalcApiUrlStart(String calcApiUrlStart) {
        this.calcApiUrlStart = calcApiUrlStart;
        log.info("urlStart set to: " + calcApiUrlStart);
    }

    public void setCalcApiUrlEnd(String calcApiUrlEnd) {
        this.calcApiUrlEnd = calcApiUrlEnd;
        log.info("urlEnd set to: " + calcApiUrlEnd);
    }

    private RestTemplate calcAPIRESTTemplate = new RestTemplate();

    @Override
    public float calculateVATofPrice(float price, float percent) throws ResourceAccessException, HttpClientErrorException.BadRequest {
        String restReq = this.calcApiUrlStart + price + this.calcApiUrlEnd + percent;
        log.info("accessing [" + restReq + "] to calc VAT of " + price + " with VAT(" + percent + "%)");
        CalcApiResponse calcApiResponse = calcAPIRESTTemplate.getForObject(restReq, CalcApiResponse.class);
        return Float.parseFloat(calcApiResponse.getVatResult());
    }
}
