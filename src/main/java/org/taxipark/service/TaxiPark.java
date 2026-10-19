package org.taxipark.service;

import org.taxipark.exception.InvalidSpeedRangeException;
import org.taxipark.model.Car;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaxiPark {
    private final List<Car> cars;

    public TaxiPark(List<Car> cars) {
        this.cars = new ArrayList<>(cars);
    }

    public double calculateTotalCost() {
        return cars.stream()
                .mapToDouble(Car::getPrice)
                .sum();
    }

    public void sortByFuelConsumption() {
        cars.sort(Comparator.comparingDouble(Car::getFuelConsumption));
    }

    public List<Car> findBySpeedRange(int minSpeed, int maxSpeed) {
        if (minSpeed > maxSpeed) {
            throw new InvalidSpeedRangeException("Мінімальна швидкість (" + minSpeed + ") не може бути більшою за максимальну (" + maxSpeed + ").");
        }

        return cars.stream()
                .filter(car -> car.getMaxSpeed() >= minSpeed && car.getMaxSpeed() <= maxSpeed)
                .toList();
    }

    public void printCars() {
        cars.forEach(System.out::println);
    }

    public List<Car> getCars() {
        return cars;
    }
}