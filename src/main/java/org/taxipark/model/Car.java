package org.taxipark.model;

public abstract class Car {
    private String brand;
    private String licensePlate;
    private int year;
    private double price;
    private double fuelConsumption;
    private int maxSpeed;
    private int passengerCapacity;

    public Car(String brand, String licensePlate, int year, double price, double fuelConsumption, int maxSpeed, int passengerCapacity) {
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.year = year;
        this.price = price;
        this.fuelConsumption = fuelConsumption;
        this.maxSpeed = maxSpeed;
        this.passengerCapacity = passengerCapacity;
    }

    public String getBrand() { return brand; }
    public String getLicensePlate() { return licensePlate; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public double getFuelConsumption() { return fuelConsumption; }
    public int getMaxSpeed() { return maxSpeed; }
    public int getPassengerCapacity() { return passengerCapacity; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d) $%.1f %s л/100км %d км/год Місць: %d",
                licensePlate, brand, year, price, fuelConsumption, maxSpeed, passengerCapacity);
    }
}