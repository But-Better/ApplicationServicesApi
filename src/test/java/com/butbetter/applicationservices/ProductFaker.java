package com.butbetter.applicationservices;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductBeverageEnum;
import com.butbetter.applicationservices.db.model.ProductRatingEnum;

import java.math.BigDecimal;

/**
 * Most importen class you have ever seen
 */
public class ProductFaker {

    private ProductFaker() {
    }

    public static final Product Frankfurter = new Product(
            ProductBeverageEnum.beer, "Frankfurter", BigDecimal.valueOf(20.99), 5, 0.5,
            ProductRatingEnum.four, 16, false, false, "Deutschland"
    );

    public static final Product Heineken = new Product(
            ProductBeverageEnum.beer, "Heineken", BigDecimal.valueOf(20.99), 5, 0.5,
            ProductRatingEnum.three, 18, false, false, "Nederland"
    );

    public static final Product Corona = new Product(
            ProductBeverageEnum.mixedBeer, "Corona Extra", BigDecimal.valueOf(27.90), 5, 0.6,
            ProductRatingEnum.three, 21, false, false, "Mexikanische"
    );

    public static final Product AbsolutVodka = new Product(
            ProductBeverageEnum.vodka, "AbsolutVodka", BigDecimal.valueOf(19.99), 40, 1,
            ProductRatingEnum.three, 21, false, true, "Russian"
    );

    public static final Product RelativeVodka = new Product(
            ProductBeverageEnum.vodka, "RelativeVodka", BigDecimal.valueOf(5.30), 40, 1,
            ProductRatingEnum.four, 21, false, true, "Belarus"
    );

    public static final Product Absinth = new Product(
            ProductBeverageEnum.wine, "Absinthe", BigDecimal.valueOf(39.90), 89.9, 0.5,
            ProductRatingEnum.one, 18, false, true, "Frankreich"
    );

    public static final Product MaikäferFlugzeugBenzin = new Product(
            ProductBeverageEnum.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(22.80), 45, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public static final Product Faxe = new Product(
            ProductBeverageEnum.custom, "Faxe", BigDecimal.valueOf(3), 8, 1,
            ProductRatingEnum.four, 18, true, true, "Deutschland"
    );

    /**
     * Get all wrong products
     */
    static public class WrongProduct {

        public static final Product nullName = new Product(
                ProductBeverageEnum.vodka, null, BigDecimal.valueOf(30.80), 45, 0.35,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product negativePrice = new Product(
                ProductBeverageEnum.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(-1.80), 45, 0.35,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product negativePercentage = new Product(
                ProductBeverageEnum.custom, "Bier Bier Bier", BigDecimal.valueOf(22.80), -45, 0.35,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product over100Percentage = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 101, 0.35,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product negativeAmount = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, -0.35,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product minAmount = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product maxAmount = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, Integer.MAX_VALUE,
                ProductRatingEnum.one, 18, true, true, "Deutschland"
        );

        public static final Product negativeAgeOfRestriction = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
                ProductRatingEnum.one, -18, true, true, "Deutschland"
        );

        public static final Product minAgeOfRestriction = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
                ProductRatingEnum.one, 0, true, true, "Deutschland"
        );

        public static final Product maxAgeOfRestriction = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
                ProductRatingEnum.one, 100, true, true, "Deutschland"
        );

        public static final Product nullCountryOfOrigin = new Product(
                ProductBeverageEnum.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
                ProductRatingEnum.one, 101, true, true, "Deutschland"
        );
    }

}
