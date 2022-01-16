package com.butbetter.applicationservices.Faker;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductBeverageEnum;
import com.butbetter.applicationservices.db.model.ProductRatingEnum;

import java.math.BigDecimal;

/**
 * Most importen class you have ever seen
 */
public class ProductFaker {

    ProductFaker() {
    }

    public final Product Frankfurter = new Product(
            ProductBeverageEnum.beer, "Frankfurter", BigDecimal.valueOf(20.99), 5, 0.5,
            ProductRatingEnum.four, 16, false, false, "Deutschland"
    );

    public final Product Heineken = new Product(
            ProductBeverageEnum.beer, "Heineken", BigDecimal.valueOf(20.99), 5, 0.5,
            ProductRatingEnum.three, 18, false, false, "Nederland"
    );

    public final Product Corona = new Product(
            ProductBeverageEnum.mixedBeer, "Corona Extra", BigDecimal.valueOf(27.90), 5, 0.6,
            ProductRatingEnum.three, 21, false, false, "Mexikanische"
    );

    public final Product AbsolutVodka = new Product(
            ProductBeverageEnum.vodka, "AbsolutVodka", BigDecimal.valueOf(19.99), 40, 1,
            ProductRatingEnum.three, 21, false, true, "Russian"
    );

    public final Product RelativeVodka = new Product(
            ProductBeverageEnum.vodka, "RelativeVodka", BigDecimal.valueOf(5.30), 40, 1,
            ProductRatingEnum.four, 21, false, true, "Belarus"
    );

    public final Product Absinth = new Product(
            ProductBeverageEnum.wine, "Absinthe", BigDecimal.valueOf(39.90), 89.9, 0.5,
            ProductRatingEnum.one, 18, false, true, "Frankreich"
    );

    public final Product MaikäferFlugzeugBenzin = new Product(
            ProductBeverageEnum.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(22.80), 45, 0.35,
            ProductRatingEnum.one, 18, true, true, "Deutschland"
    );

    public final Product Faxe = new Product(
            ProductBeverageEnum.custom, "Faxe", BigDecimal.valueOf(3), 8, 1,
            ProductRatingEnum.four, 18, true, true, "Deutschland"
    );

}
