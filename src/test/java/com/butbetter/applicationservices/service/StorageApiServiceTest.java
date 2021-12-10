package com.butbetter.applicationservices.service;

import com.butbetter.applicationservices.model.Address;
import com.butbetter.applicationservices.model.ProductInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.rmi.ServerException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

public class StorageApiServiceTest {

    private StorageApiService storageApiService;

    @BeforeEach
    void setUp() {
        this.storageApiService = new StorageApiService();
    }

    @Test
    void getOne() {
        ProductInformation productInformation = null;

        try {
            productInformation = this.storageApiService.one("6c83a6c0-40b3-4c06-a5fd-16af6344ab6d");
        } catch (ServerException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        Assertions.assertNotNull(productInformation);
    }

    @Test
    void getAll() {
        List<ProductInformation> informationList =
                null;
        try {
            informationList = this.storageApiService.all();
        } catch (ServerException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        Assertions.assertNotEquals(informationList.size(), 0);
    }

    @Test
    void createOne() {
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
                .toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));

        this.storageApiService.newProductInformation(
                new ProductInformation(
                        20,
                        new Address(
                                "name",
                                "companyName",
                                "street",
                                "city",
                                "postCode",
                                "country"
                        ),
                        offsetDateTime.toString()
                )
        );
    }
}