package com.butbetter.applicationservices;

import com.butbetter.applicationservices.model.DeepLApiResponse;
import com.butbetter.applicationservices.model.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public Language getLanguage(String sentence) throws ResourceAccessException, HttpClientErrorException.BadRequest{

        // make sure not to exceed The request body size of 128 KiB (128 * 1024 bytes)
        // i dont think our data instances will have descriptions that exceed this size

        String restReq = createURL(sentence,null);
        log.info("accessing [" + restReq + "] to get the language of this sentence [" + sentence + "]");
        DeepLApiResponse deepLApiResponse = deepLRESTTemplate.getForObject(restReq, DeepLApiResponse.class);
        if (deepLApiResponse == null)
            throw new IllegalStateException("DeepL Api response is null because spring boot didnt throw ResourcAccessException");
        Translation[] translations = deepLApiResponse.getTranslations();
        if(translations == null) throw new IllegalArgumentException("The translations are null");
        Translation translation = translations[0];
        if(translation == null) throw new IllegalArgumentException("The translation is null");

        return translation.getLanguage();
    }

    @Override
    public String translate(String sentence, Language language) throws ResourceAccessException, HttpClientErrorException.BadRequest{

        // make sure not to exceed The request body size of 128 KiB (128 * 1024 bytes)
        // i dont think our data instances will have descriptions that exceed this size

        String restReq = createURL(sentence,language);
        log.info("accessing [" + restReq + "] to translate this sentence [" + sentence + "] into " + language.getFullLanguage());
        DeepLApiResponse deepLApiResponse = deepLRESTTemplate.getForObject(restReq, DeepLApiResponse.class);
        if (deepLApiResponse == null)
            throw new IllegalStateException("DeepL Api response is null because spring boot didnt throw ResourcAccessException");
        Translation[] translations = deepLApiResponse.getTranslations();
        if(translations == null) throw new IllegalArgumentException("The translations are null");
        Translation translation = translations[0];
        if(translation == null) throw new IllegalArgumentException("The translation is null");

        return translation.getTranslation();
    }

    private String createURL(String sentence, Language language){
        return deepLAPIURL +
                authenticationKey +
                deepLToken +
                textToTranslate + sentence +
                targetLanguage + Objects.requireNonNullElse(language, Language.EN).name().toLowerCase() +
                sourceLanguage;
    }
}
