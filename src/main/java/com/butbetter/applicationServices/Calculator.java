package com.butbetter.applicationServices;

import com.butbetter.applicationServices.model.CalcApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Calculator implements CalculatorService{

    private  String CALC_API_URL_START = "https://caluationapi.herokuapp.com/calc/v1/VAT?price=";
    private  String CALC_API_URL_END = "&percent=";

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    public String getCALC_API_URL_START(){
        log.info("urlStart : " + CALC_API_URL_START);
        return this.CALC_API_URL_START;
    }

    public String getCALC_API_URL_END() {
        log.info("urlEnd : " + CALC_API_URL_END);
        return CALC_API_URL_END;
    }

    public void setCALC_API_URL_START(String CALC_API_URL_START) {
        this.CALC_API_URL_START = CALC_API_URL_START;
        log.info("urlStart set to: " + CALC_API_URL_START);
    }

    public void setCALC_API_URL_END(String CALC_API_URL_END) {
        this.CALC_API_URL_END = CALC_API_URL_END;
        log.info("urlEnd set to: " + CALC_API_URL_END);
    }

    private RestTemplate calcAPIRESTTemplate= new RestTemplate();

    @Override
    public float calculateVATofPrice(float price, float percent) {
        String restReq = this.CALC_API_URL_START + price + this.CALC_API_URL_END + percent;
        log.info("accessing [" + restReq + "] to calc VAT of " + price + " with VAT(" + percent + "%)");
        CalcApiResponse calcApiResponse = calcAPIRESTTemplate.getForObject(restReq, CalcApiResponse.class);
        return Float.parseFloat(calcApiResponse.getVatResult());
    }
}
