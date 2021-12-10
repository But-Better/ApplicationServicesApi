package com.butbetter.applicationservices.model;

import com.butbetter.applicationservices.Language;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {

    @JsonProperty("detected_source_language")
    private Language language;

    @JsonProperty("text")
    private String translation;

    public Language getLanguage() {
        return language;
    }

    public String getTranslation() {
        return translation;
    }

    public Translation(Language language, String translation) {
        this.language = language;
        this.translation = translation;
    }

    public Translation() {
    }
}
