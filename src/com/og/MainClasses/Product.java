package com.og.MainClasses;

public class Product {
    private String model;
    private String brand;
    private String type;
    private int amount;
    private boolean inShop;

    public Product(String model, String brand, String type) {
        this.model = model;
        this.brand = brand;
        this.type = type;
    }

    public Product(String model, String brand, String type, int amount, boolean inShop) {
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.amount = amount;
        this.inShop = inShop;
    }

    public Product() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isInShop() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
