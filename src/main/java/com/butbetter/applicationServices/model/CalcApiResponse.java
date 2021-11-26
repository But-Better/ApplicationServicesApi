package com.butbetter.applicationServices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalcApiResponse {

    @JsonProperty("VAT-Result")
    private String vatResult;

    public CalcApiResponse() {
    }

    public String getType() {
        return vatResult;
    }

    public void setType(String vatResult) {
        this.vatResult = vatResult;
    }

    @Override
    public String toString() {
        return "CalcApiResponse{" +
                "type='" + vatResult +
                '}';
    }
}
