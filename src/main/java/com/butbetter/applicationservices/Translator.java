package com.butbetter.applicationservices;

import com.butbetter.applicationservices.model.DeepLApiResponse;
import com.butbetter.applicationservices.model.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class Translator implements TranslatorService {

    private String deepLAPIURL = "https://api-free.deepl.com/v2/translate?";
    private String deepLToken;
    private String authenticationKey = "auth_key=";
    private String textToTranslate = "&text=";
    private String targetLanguage = "&target_lang=";
    private String sourceLanguage = "&source_lang=de"; // set to german by default

    private static final Logger log = LoggerFactory.getLogger(Translator.class);

    public Translator() {
        this.deepLToken = System.getenv("DEEPL_TOKEN");
    }

    @Autowired
    private RestTemplate deepLRESTTemplate;

    @Override
    public Language getLanguage(String text){

        DeepLApiResponse deepLApiResponse = askDeepLForTranslation(text,null);
       Translation translation = turnResponseIntoTranslation(deepLApiResponse);
       if(translation == null) return null;

        return translation.getLanguage();
    }

    @Override
    public String translate(String text, Language language){

        DeepLApiResponse deepLApiResponse = askDeepLForTranslation(text,null);
        Translation translation = turnResponseIntoTranslation(deepLApiResponse);
        if(translation == null) return null;

        return translation.getTranslation();
    }

    private Translation turnResponseIntoTranslation(DeepLApiResponse deepLApiResponse){
        if (deepLApiResponse == null) return null;
        Translation[] translations = deepLApiResponse.getTranslations();
        if(translations == null) return null;

        return translations[0];
    }

    private DeepLApiResponse askDeepLForTranslation(String text, Language language){
        String deepLRequestURL = createDeepLRequestURL(text,language);
        DeepLApiResponse deepLAPIResponse = deepLRESTTemplate.getForObject(deepLRequestURL, DeepLApiResponse.class);

        if(language == null) log.info("accessed [" + deepLRequestURL + "] to get the language of this text [" + text + "]");
        else log.info("accessed [" + deepLRequestURL + "] to translate this text [" + text + "] into " + language.getFullLanguage());

        return deepLAPIResponse;
    }

    private String createDeepLRequestURL(String text, Language language){
        return deepLAPIURL +
                authenticationKey +
                deepLToken +
                textToTranslate + text +
                targetLanguage + Objects.requireNonNullElse(language, Language.EN).name().toLowerCase() +
                sourceLanguage;
    }
}
