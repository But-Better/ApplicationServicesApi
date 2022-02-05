package com.butbetter.applicationservices.Faker;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.model.AlcoholBeverageType;
import com.butbetter.applicationservices.productapi.model.AlcoholRatingType;

import java.math.BigDecimal;

public class WrongProduct {

    WrongProduct(){

    }

    public final Alcohol emptyName = new Alcohol(
            AlcoholBeverageType.vodka, "", BigDecimal.valueOf(30.80), 45, 0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol negativePrice = new Alcohol(
            AlcoholBeverageType.custom, "Maikäfer Flugzeug Benzin", BigDecimal.valueOf(-1.80), 45, 0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol negativePercentage = new Alcohol(
            AlcoholBeverageType.custom, "Bier Bier Bier", BigDecimal.valueOf(22.80), -45, 0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol over100Percentage = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 101, 0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol negativeAmount = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, -0.35,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol minAmount = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol maxAmount = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, Integer.MAX_VALUE,
            AlcoholRatingType.one, 18, true, true, "Deutschland"
    );

    public final Alcohol negativeAgeOfRestriction = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            AlcoholRatingType.one, -18, true, true, "Deutschland"
    );

    public final Alcohol minAgeOfRestriction = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            AlcoholRatingType.one, 0, true, true, "Deutschland"
    );

    public final Alcohol maxAgeOfRestriction = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            AlcoholRatingType.one, 100, true, true, "Deutschland"
    );

    public final Alcohol emptyCountryOfOrigin = new Alcohol(
            AlcoholBeverageType.beer, "Duff", BigDecimal.valueOf(22.80), 34, 0.7,
            AlcoholRatingType.one, 101, true, true, ""
    );

}
