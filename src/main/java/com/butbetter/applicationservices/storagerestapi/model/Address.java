package com.butbetter.applicationservices.storagerestapi.model;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {

    private String id;

    private String name;

    private String companyName;

    private String street;

    private String city;

    private String postCode;

    private String country;

    public Address() {
    }

    public Address(String name, String companyName, String street, String city, String postCode, String country) {
        this.name = name;
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "uuid=" + id +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!id.equals(address.id)) return false;
        if (!Objects.equals(name, address.name)) return false;
        if (!Objects.equals(companyName, address.companyName)) return false;
        if (!Objects.equals(street, address.street)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(postCode, address.postCode)) return false;
        return Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Address cloned = new Address(this.name, this.companyName, this.street, this.city, this.postCode, this.country);
            cloned.setId(this.id);
            return cloned;
        }
    }
}
