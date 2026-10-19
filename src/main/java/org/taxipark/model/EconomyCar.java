package org.taxipark.model;

public class EconomyCar extends Car {
    private boolean hasChildSeat;

    public EconomyCar(String brand, String licensePlate, int year, double price, double fuelConsumption, int maxSpeed, int passengerCapacity, boolean hasChildSeat) {
        super(brand, licensePlate, year, price, fuelConsumption, maxSpeed, passengerCapacity);
        this.hasChildSeat = hasChildSeat;
    }

    public boolean hasChildSeat() { return hasChildSeat; }

    @Override
    public String toString() {
        return super.toString() + " Дитяче крісло: " + (hasChildSeat ? "Так" : "Ні");
    }
}