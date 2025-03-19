package com.example.lawconnect;

public class Lawyer {
    private String name;
    private String expertise;
    private int price;
    private String phone;
    private String location;

    // Default constructor for Firebase
    public Lawyer() {
        this.name = "";
        this.expertise = "";
        this.price = 0;
        this.phone = "";
        this.location = "";
    }

    // Parameterized constructor
    public Lawyer(String name, String expertise, int price, String phone, String location) {
        this.name = name;
        this.expertise = expertise;
        this.price = price;
        this.phone = phone;
        this.location = location;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}