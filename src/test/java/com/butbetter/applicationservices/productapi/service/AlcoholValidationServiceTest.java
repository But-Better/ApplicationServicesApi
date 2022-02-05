package com.butbetter.applicationservices.productapi.service;

import com.butbetter.applicationservices.faker.Faker;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlcoholValidationServiceTest {

    @Autowired
    private AlcoholValidationService alcoholValidationService;

    private final Faker faker = new Faker();

    @Test
    void checkADefaultProduct() {
        assertDoesNotThrow((() -> this.alcoholValidationService.checkProduct(faker.getAlcohol().Corona)));
    }

    @Test
    void checkNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().emptyName);
        });
    }

    @Test
    void isNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().negativePrice);
        });
    }

    @Test
    void isNegativePercentage() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().negativePercentage);
        });
    }

    @Test
    void isPercentageOver100() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().over100Percentage);
        });
    }

    @Test
    void isNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().negativeAmount);
        });
    }

    @Test
    void isMinAmount() {
        assertDoesNotThrow(() -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().minAmount);
        });
    }

    @Test
    void isMaxAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().maxAmount);
        });
    }

    @Test
    void isNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().negativeAgeOfRestriction);
        });
    }

    @Test
    void checkMinAge() {
        assertDoesNotThrow(() -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().maxAgeOfRestriction);
        });
    }

    @Test
    void checkMaxAge() {
        assertDoesNotThrow(() -> {
            this.alcoholValidationService.checkProduct(faker.getAlcohol().maxAgeOfRestriction);
        });
    }

    @Test
    void checkOverMaxAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            final Alcohol alcohol = faker.getAlcohol().maxAgeOfRestriction;
            alcohol.setAgeOfRestrictions(alcohol.getAgeOfRestrictions() + 1);
            this.alcoholValidationService.checkProduct(alcohol);
        });
    }

}