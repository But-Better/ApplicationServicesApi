package com.butbetter.applicationservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TranslatorTest {

   @Autowired
   private Translator translator;

    @Test
    @DisplayName("test german to english translation of Freund")
    void testGermanToEnglishTranslation(){
        assertEquals("Friend",translator.translate("Freund",Language.EN));
    }

    @Test
    @DisplayName("test german to english translation of Mein Freund läuft schnell.")
    void testGermanToEnglishTranslationSentence(){
        assertEquals("My friend runs fast.",translator.translate("Mein Freund läuft schnell.",Language.EN));
    }

    @Test
    @DisplayName("Test the language of a given word: Freund")
    void testLanguageTypeDetection(){
        assertEquals(Language.DE,translator.getLanguage("Freund"));
    }

}