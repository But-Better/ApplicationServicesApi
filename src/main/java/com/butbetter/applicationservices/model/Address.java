package com.butbetter.applicationservices.model;

import java.io.Serializable;
import java.util.UUID;

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
}
