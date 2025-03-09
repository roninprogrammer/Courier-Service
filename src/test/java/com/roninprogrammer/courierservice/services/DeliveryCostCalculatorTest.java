package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostCalculatorTest {
    private List<Parcel> packages;
    private List<Vehicle> vehicles;
    
    @BeforeEach
    void setUp() {
        packages = new ArrayList<>();
        vehicles = new ArrayList<>();
        packages.add(new Parcel("PKG1", 10, 100, "OFR003"));
        vehicles.add(new Vehicle(70, 200));
    }
    
    @Test
    void testDeliveryTimeEstimation() {
        DeliveryCostCalculator.calculate(packages, vehicles, null, null);
        assertEquals(1.42, packages.get(0).getDeliveryTime(), 0.01, "Delivery time should be rounded to 1.42 hours");
    }
}
