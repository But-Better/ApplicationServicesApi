package com.butbetter.applicationservices.externalAPI;

import com.butbetter.applicationservices.externalAPI.model.Language;
import com.butbetter.applicationservices.externalAPI.service.Translator;
import org.junit.jupiter.api.Disabled;
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
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("test german to english translation of Freund")
    void testGermanToEnglishTranslationWord(){
        assertEquals("Friend",translator.translate("Freund", Language.EN));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("test german to english translation of: Mein Freund läuft schnell.")
    void testGermanToEnglishTranslationSentence(){
        assertEquals("My friend runs fast.",translator.translate("Mein Freund läuft schnell.",Language.EN));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("test german to english translation of a big text")
    void testGermanToEnglishTranslationText(){
        String text = "Die reine Substanz ist eine bei Raumtemperatur farblose, leicht entzündliche Flüssigkeit mit einem brennenden Geschmack und einem charakteristischen, würzigen (süßlichen) Geruch. Die als Lebergift eingestufte Droge wird bei der Herstellung von Genussmitteln und alkoholischen Getränken wie Wein, Bier und Spirituosen aus kohlenhydrathaltigem Material durch eine von Hefen ausgelöste Gärung in großem Maßstab produziert.";
        String expectedTranslation = "The pure substance is a colorless, highly flammable liquid at room temperature with a burning taste and a characteristic spicy (sweetish) odor. The drug, which is classified as a liver toxin, is produced on a large scale during the manufacture of luxury foods and alcoholic beverages such as wine, beer and spirits from carbohydrate-containing material by fermentation triggered by yeasts.";
        assertEquals(expectedTranslation,translator.translate(text,Language.EN));
    }

    @Disabled("There seems to be a difference between the DeepL Translator and DeepL API Response but the results are basicaly equal")
    @Test
    @DisplayName("test german to english translation of a big big text")
    void testGermanToEnglishTranslationBigText(){
        String text = "Die reine Substanz ist eine bei Raumtemperatur farblose, leicht entzündliche Flüssigkeit mit einem brennenden Geschmack und einem charakteristischen, würzigen (süßlichen) Geruch. Die als Lebergift eingestufte Droge wird bei der Herstellung von Genussmitteln und alkoholischen Getränken wie Wein, Bier und Spirituosen aus kohlenhydrathaltigem Material durch eine von Hefen ausgelöste Gärung in großem Maßstab produziert.\n" + "Die Vergärung von Zucker zu Ethanol ist eine der ältesten bekannten biochemischen Reaktionen. Seit dem 19. Jahrhundert wird Ethanol für industrielle Zwecke aus Ethen hergestellt. Ethanol hat eine weite Verbreitung als Lösungsmittel für Stoffe, die für medizinische oder kosmetische Zwecke eingesetzt werden, wie Duftstoffe, Aromen, Farbstoffe oder Medikamente sowie als Desinfektionsmittel. Die chemische Industrie verwendet es sowohl als Lösungsmittel als auch als Ausgangsstoff für die Synthese weiterer Produkte wie Carbonsäureethylester.";
        String expectedTranslation = "The pure substance is a colorless, highly flammable liquid at room temperature with a burning taste and a characteristic spicy (sweetish) odor. The drug, which is classified as a liver toxin, is produced on a large scale during the manufacture of luxury foods and alcoholic beverages such as wine, beer and spirits from carbohydrate-containing material by fermentation triggered by yeasts.\n" + "The fermentation of sugars to ethanol is one of the oldest known biochemical reactions. Ethanol has been produced from ethene for industrial purposes since the 19th century. Ethanol is widely used as a solvent for substances used for medicinal or cosmetic purposes, such as fragrances, flavors, dyes or medicines, and as a disinfectant. The chemical industry uses it both as a solvent and as a starting material for the synthesis of other products such as carboxylic acid ethyl esters.";
        assertEquals(expectedTranslation,translator.translate(text,Language.EN));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("test german to japanese translation of: Hallo General Kenobi")
    void testGermanToJapaneseTranslationSentence(){
        assertEquals("ケノービ将軍こんにちは",translator.translate("Hallo General Kenobi",Language.JA));
    }


    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("Test the language of a given word: Freund")
    void testLanguageTypeDetectionDE(){
        assertEquals(Language.DE,translator.getLanguage("Freund"));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("Test the language for the sentence: suck my balls Mr Garrison")
    void testLanguageTypeDetectionEN(){
        assertEquals(Language.EN,translator.getLanguage("suck my balls Mr Garrison"));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("Test the language for the sentence: ハローケノービ")
    void testLanguageTypeDetectionJA(){
        assertEquals(Language.JA,translator.getLanguage("ハローケノービ"));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("Test empty input for translation")
    void testEmptyInputTranslation(){
        assertEquals("",translator.translate("",Language.EN));
    }

    @Test
    @Disabled("Deep L API is needed for this test to run")
    @DisplayName("Test empty input for language detection")
    void testEmptyInputLanguageDetection(){
        assertEquals(Language.EN,translator.getLanguage(""));
    }

}