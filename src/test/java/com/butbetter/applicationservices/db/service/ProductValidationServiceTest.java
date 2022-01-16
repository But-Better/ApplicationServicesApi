package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.ProductFaker;
import com.butbetter.applicationservices.db.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.Port;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductValidationServiceTest {

    @Autowired
    private ProductValidationService productValidationService;

    @Test
    void checkADefaultProduct() {
        assertDoesNotThrow((() -> this.productValidationService.checkProduct(ProductFaker.Corona)));
    }

    @Test
    void checkNameIsNotNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.nullName);
        });
    }

    @Test
    void isNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.negativePrice);
        });
    }

    @Test
    void isNegativePercentage() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.negativePercentage);
        });
    }

    @Test
    void isPercentageOver100() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.over100Percentage);
        });
    }

    @Test
    void isNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.negativeAmount);
        });
    }

    @Test
    void isMinAmount() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.minAmount);
        });
    }

    @Test
    void isMaxAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.maxAmount);
        });
    }

    @Test
    void isNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.negativeAgeOfRestriction);
        });
    }

    @Test
    void checkMinAge() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.maxAgeOfRestriction);
        });
    }

    @Test
    void checkMaxAge() {
        assertDoesNotThrow(() -> {
            this.productValidationService.checkProduct(ProductFaker.WrongProduct.maxAgeOfRestriction);
        });
    }

    @Test
    void checkOverMaxAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            Product product = ProductFaker.WrongProduct.maxAgeOfRestriction;
            product.setAgeOfRestrictions(product.getAgeOfRestrictions() + 1);
            this.productValidationService.checkProduct(product);
        });
    }

}