package com.butbetter.applicationservices.db.model;

import com.butbetter.applicationservices.model.Address;

import java.io.Serializable;

public class ProductInformation implements Serializable {

    private String uuid;

    private String deliveryTime;

    private int amount;

    private Address address;

    public ProductInformation() {
    }

    public ProductInformation(int amount, Address address, String deliveryTime) {
        this.deliveryTime = deliveryTime;
        this.amount = amount;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public Address getAddress() {
        return address;
    }

    public int getAmount() {
        return amount;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ProductInformation{" +
                "uuid=" + uuid +
                ", date=" + deliveryTime +
                ", amount=" + amount +
                ", address=" + address +
                '}';
    }
}