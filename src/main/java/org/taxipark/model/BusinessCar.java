package org.taxipark.model;

public class BusinessCar extends Car {
    private boolean hasLeatherInterior;

    public BusinessCar(String brand, String licensePlate, int year, double price, double fuelConsumption, int maxSpeed, int passengerCapacity, boolean hasLeatherInterior) {
        super(brand, licensePlate, year, price, fuelConsumption, maxSpeed, passengerCapacity);
        this.hasLeatherInterior = hasLeatherInterior;
    }

    public boolean hasLeatherInterior() { return hasLeatherInterior; }

    @Override
    public String toString() {
        return super.toString() + " Шкіра: " + (hasLeatherInterior ? "Так" : "Ні");
    }
}