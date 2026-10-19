package org.taxipark.factory;

import org.taxipark.exception.InvalidCarDataException;
import org.taxipark.model.BusinessCar;
import org.taxipark.model.Car;
import org.taxipark.model.EconomyCar;

public class CarFactory {

    public static Car createCarFromCsv(String[] values) throws InvalidCarDataException {
        if (values.length < 10) {
            throw new InvalidCarDataException("Недостатньо даних у рядку CSV. Очікується 10 колонок, знайдено: " + values.length);
        }

        try {
            String type = values[0].trim();
            String brand = values[1].trim();
            String licensePlate = values[2].trim();
            int year = Integer.parseInt(values[3].trim());
            double price = Double.parseDouble(values[4].trim());
            double fuelConsumption = Double.parseDouble(values[5].trim());
            int maxSpeed = Integer.parseInt(values[6].trim());
            int passengerCapacity = Integer.parseInt(values[7].trim());

            if (type.equalsIgnoreCase("Business")) {
                boolean hasLeather = Boolean.parseBoolean(values[9].trim());
                return new BusinessCar(brand, licensePlate, year, price, fuelConsumption, maxSpeed, passengerCapacity, hasLeather);

            } else if (type.equalsIgnoreCase("Economy")) {
                boolean hasChildSeat = Boolean.parseBoolean(values[8].trim());
                return new EconomyCar(brand, licensePlate, year, price, fuelConsumption, maxSpeed, passengerCapacity, hasChildSeat);

            } else {
                throw new InvalidCarDataException("Невідомий тип автомобіля: " + type);
            }
        } catch (NumberFormatException e) {
            throw new InvalidCarDataException("Помилка форматування чисел: " + e.getMessage());
        }
    }
}