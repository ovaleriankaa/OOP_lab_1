package org.taxipark.util;

import org.taxipark.exception.InvalidCarDataException;
import org.taxipark.factory.CarFactory;
import org.taxipark.model.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvCarParser {

    public static List<Car> parseCars(String filePath) {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                try {
                    String[] values = line.split(",");
                    Car car = CarFactory.createCarFromCsv(values);
                    cars.add(car);
                } catch (InvalidCarDataException e) {
                    System.err.println("Рядок пропущено. Знайдено пошкоджені дані: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка доступу до файлу: " + e.getMessage());
        }

        return cars;
    }
}