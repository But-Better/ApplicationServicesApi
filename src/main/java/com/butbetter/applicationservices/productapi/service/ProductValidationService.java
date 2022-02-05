package com.butbetter.applicationservices.productapi.service;

import com.butbetter.applicationservices.productapi.model.Alcohol;
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
     * @param alcohol = {@link Alcohol}
     * @throws IllegalArgumentException Name is Empty,
     *                                  Price is negative,
     *                                  Percentage min. {@value MIN_PERCENTAGE} - max. {@value MAX_PERCENTAGE},
     *                                  Age is min. {@value MIN_AGE} and max. {@value MAX_AGE},
     *                                  Amount is min {@value MIN_AMOUNT} and max. {@value MAX_AMOUNT}
     */
    public void checkProduct(Alcohol alcohol) throws IllegalArgumentException {
        if (!amountIsValid(alcohol)) throw new IllegalArgumentException("Amount is negative or to high");
        if (!percentageIsValid(alcohol)) throw new IllegalArgumentException("percentage is negative or to high");
        if (!priceIsValid(alcohol)) throw new IllegalArgumentException("price is negative");
        if (!ageRestrictionIsValid(alcohol)) throw new IllegalArgumentException("age is not valid ");
        if (alcohol.getName().isEmpty()) throw new IllegalArgumentException("name is empty");
    }

    private boolean priceIsValid(Alcohol alcohol) {
        return alcohol.getPrice().compareTo(MIN_PRICE) > 0;
    }

    private boolean ageRestrictionIsValid(Alcohol alcohol) {
        return alcohol.getAgeOfRestrictions() >= ProductValidationService.MIN_AGE
                && alcohol.getAgeOfRestrictions() <= ProductValidationService.MAX_AGE;
    }

    private boolean amountIsValid(Alcohol alcohol) {
        return alcohol.getAmount() >= MIN_AMOUNT
                && alcohol.getAmount() <= MAX_AMOUNT;
    }

    private boolean percentageIsValid(Alcohol alcohol) {
        return alcohol.getPercentage() >= MIN_PERCENTAGE
                && alcohol.getPercentage() <= MAX_PERCENTAGE;
    }
}
