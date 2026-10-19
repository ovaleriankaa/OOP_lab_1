package org.taxipark;

import org.taxipark.exception.InvalidSpeedRangeException;
import org.taxipark.model.Car;
import org.taxipark.service.TaxiPark;
import org.taxipark.util.CsvCarParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Завантаження даних з CSV");
        List<Car> loadedCars = CsvCarParser.parseCars("cars.csv");

        TaxiPark taxiPark = new TaxiPark(loadedCars);
        taxiPark.printCars();

        System.out.println("\nСумарна вартість таксопарку: $" + taxiPark.calculateTotalCost());

        System.out.println("\nВідсортовані за витратою палива");
        taxiPark.sortByFuelConsumption();
        taxiPark.printCars();

        System.out.println("\nПошук авто зі швидкістю 165 - 230 км/год");
        try {
            List<Car> fastCars = taxiPark.findBySpeedRange(165, 230);
            fastCars.forEach(System.out::println);

        } catch (InvalidSpeedRangeException e) {
            System.err.println("Виняток перехоплено: " + e.getMessage());
        }
    }
}