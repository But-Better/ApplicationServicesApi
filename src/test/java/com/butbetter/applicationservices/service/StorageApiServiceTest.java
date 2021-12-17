package com.butbetter.applicationservices.service;

import com.butbetter.applicationservices.model.Address;
import com.butbetter.applicationservices.model.ProductInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

    /**
     * Required Docker-compose up -d from StorageAPI
     */
    @Disabled
    @Test
    void getOne() {
        ProductInformation productInformation = null;

        productInformation = this.storageApiService.one("6c83a6c0-40b3-4c06-a5fd-16af6344ab6d");

        Assertions.assertNotNull(productInformation);
    }

    /**
     * Required Docker-compose up -d from StorageAPI
     */
    @Disabled
    @Test
    void getAll() {
        List<ProductInformation> informationList =
                null;
        informationList = this.storageApiService.all();

        Assertions.assertNotEquals(informationList.size(), 0);
    }

    /**
     * Required Docker-compose up -d from StorageAPI
     */
    @Disabled
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
