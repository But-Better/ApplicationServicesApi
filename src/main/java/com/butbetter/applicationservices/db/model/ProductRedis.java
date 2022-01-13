package com.butbetter.applicationservices.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("ProductRedis")
@Component
public class ProductRedis implements Serializable, Producible {
    private UUID uuid;
    private ProductBeverageEnum productBeverageEnum;
    private String name;
    private BigDecimal price;
    private double percentage;
    private double amount;
    private ProductRatingEnum productRatingEnum;
    private int ageOfRestrictions;
    private boolean fairTrade;
    private boolean bio;
    private String countryOfOrigin;

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
}
