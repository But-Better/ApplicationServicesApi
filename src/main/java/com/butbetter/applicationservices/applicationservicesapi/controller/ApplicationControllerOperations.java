package com.butbetter.applicationservices.applicationservicesapi.controller;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface ApplicationControllerOperations {

    //Product API
    ResponseEntity<?> saveAlcohol(@NotNull final Alcohol product);

    ResponseEntity<?> findAlcoholById(@NotNull final UUID id);

    ResponseEntity<?> deleteAlcoholById(@NotNull final UUID id);

    ResponseEntity<?> findAllAlcohol();

    ResponseEntity<?> deleteAllAlcohol();

    //Storage API
    ResponseEntity<?> findAllProductInformation();

    ResponseEntity<?> findProductInformationById(@NotNull final String uuid);

    ResponseEntity<?> saveProductInformation(@NotNull final ProductInformation productInformation);

    //Calu API
    ResponseEntity<?> calculateVATofPrice(float price, float percent);

    //Execptions
    ResponseEntity<?> resourceAccessException(Exception e);

    ResponseEntity<?> httpClientErrorException(Exception e);
}
