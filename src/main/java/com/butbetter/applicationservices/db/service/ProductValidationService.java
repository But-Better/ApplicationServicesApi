package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductBeverageEnum;
import com.butbetter.applicationservices.db.model.ProductRatingEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductValidationService {

    private static final Logger log = LoggerFactory.getLogger(ProductValidationService.class);

    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 100;
    private static final double MIN_AMOUNT = 0;
    private static final double MAX_AMOUNT = 20000;
    private static final double MAX_PERCENTAGE = 100;
    private static final double MIN_PERCENTAGE = 0;
    private static final BigDecimal MIN_PRICE = BigDecimal.valueOf(0);

    public void checkProduct(Product product) throws IllegalArgumentException {
        this.age(product);
        this.rating(product);
        this.name(product);
        this.price(product);
        this.percentage(product);
        this.amount(product);
    }

    private void amount(Product product) throws IllegalArgumentException {
        if (!amountIsValid(product)) {
            String message = "Amount is not valid | amount: " + product.getAmount();
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void percentage(Product product) throws IllegalArgumentException {
        if (!percentageIsValid(product)) {
            String message = "percentage is negative or to high | percentage: " + product.getPercentage();
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void price(Product product) throws IllegalArgumentException {
        if (!priceIsValid(product)) {
            String message = "price is negative | price: " + product.getPrice();
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void name(Product product) throws IllegalArgumentException {
        if (product.getName().isEmpty()) {
            String message = "name is empty";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void rating(Product product) throws IllegalArgumentException {
        try {
            ProductRatingEnum.valueOf(String.valueOf(product.getProductRatingEnum()));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    private void age(Product product) throws IllegalArgumentException {
        if (!ageRestrictionIsValid(product)) {
            final String message = "age is not valid | age: " + product.getAgeOfRestrictions();
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean priceIsValid(Product product) {
        return product.getPrice().compareTo(MIN_PRICE) > 0;
    }

    private boolean ageRestrictionIsValid(Product product) {
        return product.getAgeOfRestrictions() >= ProductValidationService.MIN_AGE
                && product.getAgeOfRestrictions() <= ProductValidationService.MAX_AGE;
    }

    private boolean amountIsValid(Product product) {
        return product.getAmount() >= MIN_AMOUNT
                && product.getAmount() <= MAX_AMOUNT;
    }

    private boolean percentageIsValid(Product product) {
        return product.getPercentage() >= MIN_PERCENTAGE
                && product.getPercentage() <= MAX_PERCENTAGE;
    }
}
