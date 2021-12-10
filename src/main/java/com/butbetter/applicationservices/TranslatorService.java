package com.butbetter.applicationservices;

public interface TranslatorService {

    /**
     * returns the language of a given word or sentence
     * @param sentence of which you want to know the language
     * @return the language as a enum Value
     */
    Language getLanguage(String sentence);

    /**
     * return the translation of the GERMAN!!! sentence into the
     * @param sentence which we want to translate
     * @param language the language into which the sentence is translated
     * @return the translated sentence as a String
     */
    String translate(String sentence, Language language);


}
