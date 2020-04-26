package com.example.oop_8;

public class Bottle {
    private String name = "Pepsi Max";
    private String manufacturer = "Pepsi";
    private double total_energy = 0.3;
    private double size = 0.5;
    private double price = 1.80;

    public Bottle() {}

    public Bottle(String name, String manuf, double totE, double size, double price) {
        this.name = name;
        this.manufacturer = manuf;
        this.total_energy = totE;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public double getEnergy() {
        return this.total_energy;
    }

    public double getSize() {
        return this.size;
    }

    public double getPrice() {
        return this.price;
    }
}
