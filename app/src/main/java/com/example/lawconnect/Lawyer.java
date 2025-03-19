package com.example.lawconnect;

public class Lawyer {
    private String name;
    private String expertise;
    private int price;

    // Default constructor for Firebase
    public Lawyer() {
        this.name = "";
        this.expertise = "";
        this.price = 0;
    }

    // Parameterized constructor
    public Lawyer(String name, String expertise, int price) {
        this.name = name;
        this.expertise = expertise;
        this.price = price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}