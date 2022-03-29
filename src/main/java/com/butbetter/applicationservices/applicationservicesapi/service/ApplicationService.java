package com.butbetter.applicationservices.applicationservicesapi.service;

import com.butbetter.applicationservices.caluapi.service.CalculatorService;
import com.butbetter.applicationservices.csvExporter.ProductInformationCSVFileExporter;
import com.butbetter.applicationservices.externalAPI.model.Language;
import com.butbetter.applicationservices.externalAPI.service.Translator;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.service.AlcoholService;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.butbetter.applicationservices.storagerestapi.service.StorageApiService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService implements ApplicationOperations {

    private final AlcoholService alcoholService;
    private final StorageApiService storageApiService;
    private final ProductInformationCSVFileExporter exporter;
    private final CalculatorService calculatorService;
    private final Translator translator;

    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    public ApplicationService(AlcoholService alcoholService, StorageApiService storageApiService, CalculatorService calculatorService, Translator translator, ProductInformationCSVFileExporter exporter) {
        this.alcoholService = alcoholService;
        this.storageApiService = storageApiService;
        this.calculatorService = calculatorService;
        this.translator = translator;
        this.exporter = exporter;
    }

    @Override
    public float calculateVATofPrice(float price, float percent) throws ResourceAccessException, HttpClientErrorException.BadRequest {
        log.info(String.format("%s calculateVATofPrice", this.getClass()));
        return this.calculatorService.calculateVATofPrice(price, percent);
    }

    @Override
    public void saveAlcohol(Alcohol product) {
        log.info(String.format("%s save Alcohol", this.getClass()));
        this.alcoholService.saveAlcohol(product);
    }

    @Override
    public Alcohol findAlcoholById(UUID uuid) {
        log.info(String.format("%s findById Alcohol", this.getClass()));
        return this.alcoholService.findAlcoholById(uuid);
    }

    @Override
    public void deleteAlcoholById(UUID id) {
        log.info(String.format("%s deleteById Alcohol", this.getClass()));
        this.alcoholService.deleteAlcoholById(id);
    }

    @Override
    public Iterable<Alcohol> findAllAlcohol() {
        log.info(String.format("%s findAll Alcohol", this.getClass()));
        return this.alcoholService.findAllAlcohol();
    }

    @Override
    public void deleteAllAlcohol() {
        log.info(String.format("%s deleteAll Alcohol", this.getClass()));
        this.alcoholService.deleteAllAlcohol();
    }

    @Override
    public List<ProductInformation> findAllProductInformation() {
        log.info(String.format("%s all ProductInformation", this.getClass()));
        return this.storageApiService.findAllProductInformation();
    }

    @Override
    public ProductInformation findProductInformationById(String uuid) {
        log.info(String.format("%s get one ProductInformation", this.getClass()));
        return this.storageApiService.findProductInformationById(uuid);
    }

    @SneakyThrows
    @Override
    public void saveProductInformation(ProductInformation productInformation) {
        log.info(String.format("%s create a ProductInformation", this.getClass()));
        this.storageApiService.saveProductInformation(productInformation);
        this.exporter.export(productInformation);
    }

    @Override
    public Language getLanguage(String text) {
        log.info(String.format("%s get language", this.getClass()));
        return this.translator.getLanguage(text);
    }

    @Override
    public String translate(String text, Language language) {
        log.info(String.format("%s translate german input into " + language, this.getClass()));
        return this.translator.translate(text,language);
    }
}
