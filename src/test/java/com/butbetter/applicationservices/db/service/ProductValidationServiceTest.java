package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.Faker.Faker;
import com.butbetter.applicationservices.Faker.ProductFaker;
import com.butbetter.applicationservices.Faker.WrongProduct;
import com.butbetter.applicationservices.db.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductValidationServiceTest {

    @Autowired
    private ProductValidationService productValidationService;

    private final Faker faker = new Faker();

    @Test
    void checkADefaultProduct() {
        assertDoesNotThrow((() -> this.productValidationService.checkProduct(faker.getProductFaker().Corona)));
    }

    @Test
    void checkNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().emptyName);
        });
    }

    @Test
    void isNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().negativePrice);
        });
    }

    @Test
    void isNegativePercentage() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().negativePercentage);
        });
    }

    @Test
    void isPercentageOver100() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().over100Percentage);
        });
    }

    @Test
    void isNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().negativeAmount);
        });
    }

    @Test
    void isMinAmount() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().minAmount);
        });
    }

    @Test
    void isMaxAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().maxAmount);
        });
    }

    @Test
    void isNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().negativeAgeOfRestriction);
        });
    }

    @Test
    void checkMinAge() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().maxAgeOfRestriction);
        });
    }

    @Test
    void checkMaxAge() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(faker.getWrongProduct().maxAgeOfRestriction);
        });
    }

    @Test
    void checkOverMaxAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            final Product product = faker.getWrongProduct().maxAgeOfRestriction;
            product.setAgeOfRestrictions(product.getAgeOfRestrictions() + 1);
            this.productValidationService.checkProduct(product);
        });
    }

}