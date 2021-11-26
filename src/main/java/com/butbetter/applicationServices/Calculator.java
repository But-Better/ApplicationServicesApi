package com.butbetter.applicationServices;

import com.butbetter.applicationServices.model.CalcApiResponse;
import org.springframework.web.client.RestTemplate;

public class Calculator implements CalculatorService{

    private final String CALC_API_URL_START;
    private final String CALC_API_URL_END;

    private RestTemplate calcAPIRESTTemplate= new RestTemplate();

    public Calculator(){
        this.CALC_API_URL_START = "https://caluationapi.herokuapp.com/calc/v1/VAT?price=";
        this.CALC_API_URL_END = "&percent=";
    }

    public Calculator(String urlStart, String urlEnd){
        this.CALC_API_URL_START = urlStart;
        this.CALC_API_URL_END = urlEnd;
    }

    @Override
    public float calculateVATofPrice(float price, float percent) {
        String restReq = this.CALC_API_URL_START + price + this.CALC_API_URL_END + percent;
        CalcApiResponse calcApiResponse = calcAPIRESTTemplate.getForObject(restReq, CalcApiResponse.class);
        return Float.parseFloat(calcApiResponse.getVatResult());
    }
}
