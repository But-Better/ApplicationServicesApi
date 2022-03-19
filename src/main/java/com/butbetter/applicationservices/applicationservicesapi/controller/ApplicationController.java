package com.butbetter.applicationservices.applicationservicesapi.controller;

import com.butbetter.applicationservices.applicationservicesapi.service.ApplicationService;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.UUID;

@RestController
@RequestMapping(value = "v1/application")
public class ApplicationController implements ApplicationControllerOperations {

    private final ApplicationService applicationService;

    private final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private static final String ALCOHOL_API_TAG = "alcohol";
    private static final String PRODUCT_INFORMATION_API_TAG = "productinformation";
    private static final String CALCULATION_API_TAG = "VAT";

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/" + ALCOHOL_API_TAG)
    @Override
    public ResponseEntity<?> saveAlcohol(@RequestParam("alcohol") Alcohol product) {
        this.applicationService.saveAlcohol(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/" + ALCOHOL_API_TAG + "/{id}")
    @Override
    public ResponseEntity<?> findAlcoholById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(this.applicationService.findAlcoholById(id));
    }

    @DeleteMapping("/" + ALCOHOL_API_TAG + "/{id}")
    @Override
    public ResponseEntity<?> deleteAlcoholById(@PathVariable UUID id) {
        this.applicationService.deleteAlcoholById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/" + ALCOHOL_API_TAG)
    @Override
    public ResponseEntity<?> findAllAlcohol() {
        return ResponseEntity.ok().body(this.applicationService.findAllAlcohol());
    }

    @DeleteMapping("/" + ALCOHOL_API_TAG)
    @Override
    public ResponseEntity<?> deleteAllAlcohol() {
        this.applicationService.deleteAllAlcohol();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/" + PRODUCT_INFORMATION_API_TAG)
    @Override
    public ResponseEntity<?> findAllProductInformation() {
        return ResponseEntity.ok().body(this.applicationService.findAllProductInformation());
    }

    @GetMapping("/" + PRODUCT_INFORMATION_API_TAG + "/{id}")
    @Override
    public ResponseEntity<?> findProductInformationById(@PathVariable String id) {
        return ResponseEntity.ok().body(this.applicationService.findProductInformationById(id));
    }

    @PostMapping("/" + PRODUCT_INFORMATION_API_TAG)
    @Override
    public ResponseEntity<?> saveProductInformation(@RequestParam("productInformation") ProductInformation productInformation) {
        this.applicationService.saveProductInformation(productInformation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/" + CALCULATION_API_TAG)
    @Override
    public ResponseEntity<?> calculateVATofPrice(
            @RequestParam(value = "price") float price,
            @RequestParam(value = "percent") float percent) {
        return ResponseEntity.ok().body(this.applicationService.calculateVATofPrice(price, percent));
    }

    @ExceptionHandler(ResourceAccessException.class)
    @Override
    public ResponseEntity<?> resourceAccessException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @Override
    public ResponseEntity<?> httpClientErrorException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }

}
