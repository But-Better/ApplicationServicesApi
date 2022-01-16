package com.butbetter.applicationservices.Faker;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductBeverageEnum;
import com.butbetter.applicationservices.db.model.ProductRatingEnum;

import java.math.BigDecimal;

public class WrongProduct {

    WrongProduct(){

    }

    public final Product emptyName = new Product(
            ProductBeverageEnum.vodka, "", BigDecimal.valueOf(30.80), 45, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product negativePrice = new Product(
            ProductBeverageEnum.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(-1.80), 45, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product negativePercentage = new Product(
            ProductBeverageEnum.custom, "Bier Bier Bier", BigDecimal.valueOf(22.80), -45, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product over100Percentage = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 101, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product negativeAmount = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, -0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product minAmount = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product maxAmount = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, Integer.MAX_VALUE,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product negativeAgeOfRestriction = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            ProductRatingEnum.one, -18, true, true, "Deutschland"
    );

    public final Product minAgeOfRestriction = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            ProductRatingEnum.one, 0, true, true, "Deutschland"
    );

    public final Product maxAgeOfRestriction = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            ProductRatingEnum.one, 100, true, true, "Deutschland"
    );

    public final Product emptyCountryOfOrigin = new Product(
            ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            ProductRatingEnum.one, 101, true, true, ""
    );

}
