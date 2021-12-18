package com.butbetter.applicationservices.db.model;

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
@Table(schema = "Product")
public class Product implements Serializable {

    @Id
    @Column(name = "uuid", nullable = false, updatable = false, insertable = false)
    private UUID uuid;

    @Enumerated(EnumType.ORDINAL)
    private ProductBeverageEnum productBeverageEnum;

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
    private ProductRatingEnum productRatingEnum;

    @Min(16)
    private int ageOfRestrictions;

    private boolean fairTrade;
    private boolean bio;

    @NotNull
    @NotBlank
    private String countryOfOrigin;

    /**
     * Object of Product for Hibernate
     *
     * @param uuid                = Random id
     * @param productBeverageEnum = [beer, wine, sparklingWine, vodka, rum, gin]
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
    public Product(UUID uuid, ProductBeverageEnum productBeverageEnum, String name,
                   BigDecimal price, double percentage, double amount, ProductRatingEnum productRatingEnum,
                   int ageOfRestrictions, boolean fairTrade, boolean bio, String countryOfOrigin
    ) {
        this.uuid = uuid;
        this.productBeverageEnum = productBeverageEnum;
        this.name = name;
        this.price = price;
        this.percentage = percentage;
        this.amount = amount;
        this.productRatingEnum = productRatingEnum;
        this.ageOfRestrictions = ageOfRestrictions;
        this.fairTrade = fairTrade;
        this.bio = bio;
        this.countryOfOrigin = countryOfOrigin;
    }

    /**
     * Object of Product for Hibernate
     *
     * @param productBeverageEnum = [beer, wine, sparklingWine, vodka, rum, gin]
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
    public Product(ProductBeverageEnum productBeverageEnum, String name, BigDecimal price, double percentage,
                   double amount, ProductRatingEnum productRatingEnum, int ageOfRestrictions, boolean fairTrade,
                   boolean bio, String countryOfOrigin
    ) {
        this.productBeverageEnum = productBeverageEnum;
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

    public Product() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProductBeverageEnum getProductBeverageEnum() {
        return productBeverageEnum;
    }

    public void setProductBeverageEnum(ProductBeverageEnum productBeverageEnum) {
        this.productBeverageEnum = productBeverageEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ProductRatingEnum getProductRatingEnum() {
        return productRatingEnum;
    }

    public void setProductRatingEnum(ProductRatingEnum productRatingEnum) {
        this.productRatingEnum = productRatingEnum;
    }

    public int getAgeOfRestrictions() {
        return ageOfRestrictions;
    }

    public void setAgeOfRestrictions(int ageOfRestrictions) {
        this.ageOfRestrictions = ageOfRestrictions;
    }

    public boolean isFairTrade() {
        return fairTrade;
    }

    public void setFairTrade(boolean fairTrade) {
        this.fairTrade = fairTrade;
    }

    public boolean isBio() {
        return bio;
    }

    public void setBio(boolean bio) {
        this.bio = bio;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.percentage, percentage) == 0 && Double.compare(product.amount, amount) == 0
                && ageOfRestrictions == product.ageOfRestrictions && fairTrade == product.fairTrade
                && bio == product.bio && Objects.equals(uuid, product.uuid) && productBeverageEnum == product.productBeverageEnum
                && Objects.equals(name, product.name) && Objects.equals(price, product.price)
                && productRatingEnum == product.productRatingEnum
                && Objects.equals(countryOfOrigin, product.countryOfOrigin
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, productBeverageEnum, name, price, percentage, amount,
                productRatingEnum, ageOfRestrictions, fairTrade, bio, countryOfOrigin
        );
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", productBeverageEnum=" + productBeverageEnum +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", percentage=" + percentage +
                ", amount=" + amount +
                ", productRatingEnum=" + productRatingEnum +
                ", ageOfRestrictions=" + ageOfRestrictions +
                ", fairTrade=" + fairTrade +
                ", bio=" + bio +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }
}
