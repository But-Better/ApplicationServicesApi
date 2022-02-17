package com.butbetter.applicationservices.externalAPI.model;

public enum Language {
    DE("German"),
    EN("English"),
    BG("Bulgarian"),
    CS("Czech"),
    DA("Danish"),
    EL("Greek"),
    ES("Spanish"),
    ET("Estonian"),
    FI("Finnish"),
    FR("French"),
    HU("Hungarian"),
    IT("Italian"),
    JA("Japanese"),
    LT("Lithuanian"),
    LV("Latvian"),
    NL("Dutch"),
    PL("Polish"),
    PT("Portuguese"),
    RO("Romanian"),
    RU("Russian"),
    SK("Slovak"),
    SL("Slovenian"),
    SV("Swedish"),
    ZH("Chinese");

    private final String fullLanguage;

    Language(String language){
        this.fullLanguage = language;
    }

    public String getFullLanguage() {
        return fullLanguage;
    }

}
