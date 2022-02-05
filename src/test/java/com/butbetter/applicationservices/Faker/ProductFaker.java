package com.butbetter.applicationservices.Faker;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.model.AlcoholBeverageType;
import com.butbetter.applicationservices.productapi.model.AlcoholRatingType;

import java.math.BigDecimal;

/**
 * Most importen class you have ever seen
 */
public class ProductFaker {

    ProductFaker() {
    }

    public final Alcohol Frankfurter = new Alcohol(
            AlcoholBeverageType.beer, "Frankfurter", BigDecimal.valueOf(20.99), 5, 0.5,
            AlcoholRatingType.four, 16, false, false, "Deutschland"
    );

    public final Alcohol Heineken = new Alcohol(
            AlcoholBeverageType.beer, "Heineken", BigDecimal.valueOf(20.99), 5, 0.5,
            AlcoholRatingType.three, 18, false, false, "Nederland"
    );

    public final Alcohol Corona = new Alcohol(
            AlcoholBeverageType.mixedBeer, "Corona Extra", BigDecimal.valueOf(27.90), 5, 0.6,
            AlcoholRatingType.three, 21, false, false, "Mexikanische"
    );

    public final Alcohol AbsolutVodka = new Alcohol(
            AlcoholBeverageType.vodka, "AbsolutVodka", BigDecimal.valueOf(19.99), 40, 1,
            AlcoholRatingType.three, 21, false, true, "Russian"
    );

    public final Alcohol RelativeVodka = new Alcohol(
            AlcoholBeverageType.vodka, "RelativeVodka", BigDecimal.valueOf(5.30), 40, 1,
            AlcoholRatingType.four, 21, false, true, "Belarus"
    );

    public final Alcohol Absinth = new Alcohol(
            AlcoholBeverageType.wine, "Absinthe", BigDecimal.valueOf(39.90), 89.9, 0.5,
            AlcoholRatingType.one, 18, false, true, "Frankreich"
    );

    public final Alcohol MaikäferFlugzeugBenzin = new Alcohol(
            AlcoholBeverageType.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(22.80), 45, 0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol Faxe = new Alcohol(
            AlcoholBeverageType.custom, "Faxe", BigDecimal.valueOf(3), 8, 1,
            AlcoholRatingType.four, 18, true, true, "Deutschland"
    );

}
