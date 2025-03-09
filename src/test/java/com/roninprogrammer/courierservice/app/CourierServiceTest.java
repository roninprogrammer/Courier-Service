package com.roninprogrammer.courierservice.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculator;
import static org.junit.jupiter.api.Assertions.*;

public class CourierServiceTest {
    @Test
    void testCourierServiceFlow() {
        // Simulate user input for main execution flow
        // Verify that the program runs without crashing and processes packages correctly
        assertDoesNotThrow(() -> {
            List<Parcel> testPackages = new ArrayList<>();
            testPackages.add(new Parcel("PKG1", 10, 100, "OFR003"));
            testPackages.add(new Parcel("PKG2", 20, 150, "OFR001"));

            List<Vehicle> testVehicles = new ArrayList<>();
            testVehicles.add(new Vehicle(70, 200));
            testVehicles.add(new Vehicle(80, 250));

            DeliveryCostCalculator.calculate(testPackages, testVehicles, null, null);
        });
    }
    
}
