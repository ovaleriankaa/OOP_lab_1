package org.taxipark.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.taxipark.exception.InvalidSpeedRangeException;
import org.taxipark.model.Car;
import org.taxipark.util.CsvCarParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxiParkTest {
    private TaxiPark taxiPark;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        List<String> csvLines = Arrays.asList(
                "Type,Brand,LicensePlate,Year,Price,FuelConsumption,MaxSpeed,PassengerCapacity,HasChildSeat,HasLeather",
                "Economy,Daewoo Lanos,AA0001KA,2015,3000,8.5,160,4,true,false",
                "Economy,Renault Logan,BC1234A0,2019,5000,6.2,170,4,false,false",
                "Business,Toyota Camry,KA7777BB,2023,30000,9.0,220,4,false,true"
        );

        Path tempFile = tempDir.resolve("test_cars.csv");
        Files.write(tempFile, csvLines);

        List<Car> loadedCars = CsvCarParser.parseCars(tempFile.toAbsolutePath().toString());
        taxiPark = new TaxiPark(loadedCars);
    }

    @Test
    void testCalculateTotalCost() {
        double expected = taxiPark.getCars().stream()
                .mapToDouble(Car::getPrice)
                .sum();

        assertEquals(expected, taxiPark.calculateTotalCost(), 0.01, "Динамічна сума розрахована неправильно");
    }

    @Test
    void testSortByFuelConsumption() {
        taxiPark.sortByFuelConsumption();
        List<Car> sorted = taxiPark.getCars();

        assertAll("Перевірка комплексної правильності сортування",
                () -> assertEquals(6.2, sorted.get(0).getFuelConsumption(), "Перше авто має бути найекономнішим (Logan)"),
                () -> assertEquals(8.5, sorted.get(1).getFuelConsumption(), "Друге авто має середню витрату (Lanos)"),
                () -> assertEquals(9.0, sorted.get(2).getFuelConsumption(), "Третє авто має найвищу витрату (Camry)"),
                () -> assertTrue(sorted.get(0).getFuelConsumption() <= sorted.get(1).getFuelConsumption())
        );
    }

    @ParameterizedTest(name = "Діапазон швидкості {0} - {1}, очікується знайти {2} авто")
    @CsvSource({
            "100, 250, 3",
            "165, 250, 2",
            "160, 160, 1",
            "300, 400, 0"
    })
    void testFindBySpeedRange(int minSpeed, int maxSpeed, int expectedCount) {
        List<Car> found = taxiPark.findBySpeedRange(minSpeed, maxSpeed);
        assertEquals(expectedCount, found.size(), "Неправильна кількість знайдених авто для діапазону " + minSpeed + "-" + maxSpeed);
    }

    @Test
    void testFindBySpeedRangeException() {
        InvalidSpeedRangeException exception = assertThrows(
                InvalidSpeedRangeException.class,
                () -> taxiPark.findBySpeedRange(200, 100)
        );
        assertNotNull(exception.getMessage(), "Повідомлення винятку не повинно бути порожнім");
    }

    @Test
    void testEmptyTaxiPark() {
        TaxiPark emptyPark = new TaxiPark(new ArrayList<>());

        assertAll("Перевірка поведінки порожнього парку",
                () -> assertEquals(0.0, emptyPark.calculateTotalCost()),
                () -> assertTrue(emptyPark.getCars().isEmpty()),
                () -> assertTrue(emptyPark.findBySpeedRange(0, 100).isEmpty())
        );
    }

    @Test
    void testCalculateTotalCostMockito() {
        Car mockCar1 = Mockito.mock(Car.class);
        Car mockCar2 = Mockito.mock(Car.class);

        Mockito.when(mockCar1.getPrice()).thenReturn(5500.50);
        Mockito.when(mockCar2.getPrice()).thenReturn(10200.75);

        TaxiPark parkWithMocks = new TaxiPark(Arrays.asList(mockCar1, mockCar2));

        assertEquals(15701.25, parkWithMocks.calculateTotalCost(), 0.01);

        Mockito.verify(mockCar1, Mockito.times(1)).getPrice();
        Mockito.verify(mockCar2, Mockito.times(1)).getPrice();
    }
}