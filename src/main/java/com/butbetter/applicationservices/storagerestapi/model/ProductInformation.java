package com.butbetter.applicationservices.storagerestapi.model;

import com.butbetter.applicationservices.storagerestapi.model.Address;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInformation that = (ProductInformation) o;

        if (amount != that.amount) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!Objects.equals(deliveryTime, that.deliveryTime)) return false;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch(CloneNotSupportedException e) {
            ProductInformation cloned = new ProductInformation(this.amount, (Address) this.address.clone(), this.deliveryTime);
            cloned.setUuid(this.getUuid());
            return cloned;
        }
    }
}