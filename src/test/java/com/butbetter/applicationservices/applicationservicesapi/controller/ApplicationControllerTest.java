package com.butbetter.applicationservices.applicationservicesapi.controller;

import com.butbetter.applicationservices.applicationservicesapi.service.ApplicationService;
import com.butbetter.applicationservices.caluapi.service.CalculatorService;
import com.butbetter.applicationservices.externalAPI.service.Translator;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.model.AlcoholBeverageType;
import com.butbetter.applicationservices.productapi.model.AlcoholRatingType;
import com.butbetter.applicationservices.productapi.service.AlcoholService;
import com.butbetter.applicationservices.storagerestapi.service.StorageApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ApplicationControllerTest {

    private ApplicationController applicationController;

    @SuppressWarnings("FieldCanBeLocal")
    private ApplicationService applicationService;

    @Autowired
    private AlcoholService alcoholService;

    @SuppressWarnings("FieldCanBeLocal")
    private StorageApiService storageApiService;
    @SuppressWarnings("FieldCanBeLocal")
    private CalculatorService calculatorService;

    private Translator translator;

    @BeforeEach
    void setUp() {
        storageApiService = mock(StorageApiService.class);
        calculatorService = mock(CalculatorService.class);
        translator = mock(Translator.class);

        applicationService = new ApplicationService(alcoholService, storageApiService, calculatorService, translator);

        applicationController = new ApplicationController(applicationService);
    }

    @Test
    void saveAlcohol() {
        Alcohol alcohol = new Alcohol(AlcoholBeverageType.valueOf("beer"), "Beer", new BigDecimal("7.61"), 8.5, 1, AlcoholRatingType.valueOf("five"), 18, true, true, "Germany");
        applicationController.saveAlcohol(alcohol);
        System.out.println(applicationController.findAllAlcohol());
    }

    @Test
    void findAllAlcohol() {
    }
}