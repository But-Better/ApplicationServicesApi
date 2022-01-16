package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductBeverageEnum;
import com.butbetter.applicationservices.db.model.ProductRatingEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductValidationService {

    public static final int MIN_AGE_RESTRICTIONS = 0;
    public static final int MAX_AGE_RESTRICTIONS = 100;

    private static final Logger log = LoggerFactory.getLogger(ProductValidationService.class);

    public void checkProduct(Product product) throws IllegalArgumentException {
        this.ageRestriction(product);
        this.beverage(product);
        this.rating(product);
    }

    private void rating(Product product) throws IllegalArgumentException {
        try {
            ProductRatingEnum.valueOf(String.valueOf(product.getProductRatingEnum()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void beverage(Product product) throws IllegalArgumentException {
        try {
            ProductBeverageEnum.valueOf(String.valueOf(product.getProductBeverageEnum()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void ageRestriction(Product product) throws IllegalArgumentException {
        if (ageRestrictionIsValid(product)) {
            final String message = "age of restriction is smaller than 0 or bigger then 100";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean ageRestrictionIsValid(Product product) {
        return product.getAgeOfRestrictions() <= ProductValidationService.MIN_AGE_RESTRICTIONS
                && product.getAgeOfRestrictions() >= ProductValidationService.MAX_AGE_RESTRICTIONS;
    }

}
