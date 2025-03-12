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

    @Test
    void testDeliveryOrder() {
        List<Parcel> packages = new ArrayList<>();
        packages.add(new Parcel("PKG1", 50, 30, "OFR001"));  // 1) Lighter package
        packages.add(new Parcel("PKG2", 75, 125, "OFR008")); // 2) Heavier package
        packages.add(new Parcel("PKG3", 175, 100, "OFR003")); // 2_ Heaviest package

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(70, 200)); // Vehicle speed: 70 km/hr, max weight: 200kg

        DeliveryCostCalculator.calculate(packages, vehicles);

        assertEquals(1.42, packages.get(2).getEstimatedDeliveryTime(), 0.01, "PKG3 should be delivered first (heaviest)");
        assertEquals(1.78, packages.get(1).getEstimatedDeliveryTime(), 0.01, "PKG2 should be delivered next (next heaviest)");
        assertEquals(3.98, packages.get(0).getEstimatedDeliveryTime(), 0.01, "PKG1 should be delivered last (lightest)");
    }

    @Test
    void testMultipleVehicles() {
        List<Parcel> packages = new ArrayList<>();
        packages.add(new Parcel("PKG1", 50, 30, "OFR001"));
        packages.add(new Parcel("PKG2", 75, 125, "OFR008"));
        packages.add(new Parcel("PKG3", 175, 100, "OFR003"));
        packages.add(new Parcel("PKG4", 110, 60, "OFR002"));
        packages.add(new Parcel("PKG5", 155, 95, "NA"));

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(70, 200)); // Vehicle 1
        vehicles.add(new Vehicle(70, 200)); // Vehicle 2

        DeliveryCostCalculator.calculate(packages, vehicles);

        assertEquals(3.98, packages.get(0).getEstimatedDeliveryTime(), 0.01);
        assertEquals(1.78, packages.get(1).getEstimatedDeliveryTime(), 0.01);
        assertEquals(1.42, packages.get(2).getEstimatedDeliveryTime(), 0.01);
        assertEquals(0.85, packages.get(3).getEstimatedDeliveryTime(), 0.01);
        assertEquals(4.19, packages.get(4).getEstimatedDeliveryTime(), 0.01);
    }
}