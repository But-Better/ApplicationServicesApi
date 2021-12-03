package com.butbetter.applicationservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalcApiResponse {

    @JsonProperty("VAT-Result")
    private String vatResult;

    public CalcApiResponse() {
    }

    public CalcApiResponse(String vatResult){
        this.vatResult = vatResult;
    }

    public String getVatResult() {
        return vatResult;
    }

    public void setVatResult(String vatResult) {
        this.vatResult = vatResult;
    }

    @Override
    public String toString() {
        return "CalcApiResponse{" +
                "type='" + vatResult +
                '}';
    }
}
