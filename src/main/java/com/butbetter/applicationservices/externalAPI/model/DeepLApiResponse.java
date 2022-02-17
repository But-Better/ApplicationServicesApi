package com.butbetter.applicationservices.externalAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepLApiResponse {

    @JsonProperty("translations")
    private Translation[] translations;

    public Translation[] getTranslations() {
        return translations;
    }

    public DeepLApiResponse(){

    }

    public DeepLApiResponse(Translation[] translations) {
        this.translations = translations;
    }
}
