package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.db.model.Product;
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
    private static final double MIN_PERCENTAGE = 1;
    private static final BigDecimal MIN_PRICE = BigDecimal.valueOf(0);

    /**
     * Check is Amount, Name, Price, Percentage or Age valid to processes it
     *
     * @param product = {@link Product}
     * @throws IllegalArgumentException Name is Empty,
     *                                  Price is negative,
     *                                  Percentage min. {@value MIN_PERCENTAGE} - max. {@value MAX_PERCENTAGE},
     *                                  Age is min. {@value MIN_AGE} and max. {@value MAX_AGE},
     *                                  Amount is min {@value MIN_AMOUNT} and max. {@value MAX_AMOUNT}
     */
    public void checkProduct(Product product) throws IllegalArgumentException {
        if (!amountIsValid(product)) throw new IllegalArgumentException("Amount is negative or to high");
        if (!percentageIsValid(product)) throw new IllegalArgumentException("percentage is negative or to high");
        if (!priceIsValid(product)) throw new IllegalArgumentException("price is negative");
        if (!ageRestrictionIsValid(product)) throw new IllegalArgumentException("age is not valid ");
        if (product.getName().isEmpty()) throw new IllegalArgumentException("name is empty");
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
