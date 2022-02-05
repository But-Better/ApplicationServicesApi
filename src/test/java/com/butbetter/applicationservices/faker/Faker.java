package com.butbetter.applicationservices.faker;

public class Faker {

    private final AlcoholFaker alcoholFaker;

    public Faker() {
        this.alcoholFaker = new AlcoholFaker();
    }

    public AlcoholFaker getAlcohol() {
        return alcoholFaker;
    }
}
