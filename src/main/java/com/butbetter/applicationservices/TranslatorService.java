package com.butbetter.applicationservices;

public interface TranslatorService {

    /**
     * returns the language of a given text
     * @param text of which you want to know the language
     *             make sure not to exceed The request body size of 128 KiB (128 * 1024 bytes)
     * @return the language as a enum Value
     */
    Language getLanguage(String text);

    /**
     * returns the translation of the GERMAN!!! text into the defined target language
     * @param text which we want to translate
     *             make sure not to exceed The request body size of 128 KiB (128 * 1024 bytes)
     * @param language the language into which the text is translated
     * @return the translated text as a String
     */
    String translate(String text, Language language);


}
