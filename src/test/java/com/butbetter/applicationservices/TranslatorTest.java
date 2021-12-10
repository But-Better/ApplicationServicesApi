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
}