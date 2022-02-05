package com.butbetter.applicationservices.productapi.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Alcohol implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Enumerated(EnumType.ORDINAL)
    private AlcoholBeverageType alcoholBeverageType;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @Min(1)
    @Max(100)
    private double percentage;

    @Min(0)
    @Max(999999999)
    private double amount;

    @Enumerated(EnumType.ORDINAL)
    private AlcoholRatingType productRatingEnum;

    @Min(16)
    private int ageOfRestrictions;

    private boolean fairTrade;
    private boolean bio;

    @NotNull
    @NotBlank
    private String countryOfOrigin;

    /**
     * Model of alcohol
     *
     * @param alcoholBeverageType = [beer, wine, sparklingWine, vodka, rum, gin]
     * @param name                = name of Product
     * @param price               = price like €/$/¥
     * @param percentage          = power of drink
     * @param amount              = how much liter have the drink
     * @param productRatingEnum   = [zero, one, two, three, four, five]
     * @param ageOfRestrictions   = min 16. years old
     * @param fairTrade           = true/false
     * @param bio                 = true/false
     * @param countryOfOrigin     = name of origin in which country
     */
    public Alcohol(AlcoholBeverageType alcoholBeverageType, String name, BigDecimal price, double percentage,
                   double amount, AlcoholRatingType productRatingEnum, int ageOfRestrictions, boolean fairTrade,
                   boolean bio, String countryOfOrigin
    ) {
        this.alcoholBeverageType = alcoholBeverageType;
        this.name = name;
        this.price = price;
        this.percentage = percentage;
        this.amount = amount;
        this.productRatingEnum = productRatingEnum;
        this.ageOfRestrictions = ageOfRestrictions;
        this.fairTrade = fairTrade;
        this.bio = bio;
        this.countryOfOrigin = countryOfOrigin;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Alcohol alcohol = (Alcohol) o;
        return uuid != null && Objects.equals(uuid, alcohol.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
