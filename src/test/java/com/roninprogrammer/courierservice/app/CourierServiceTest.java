package com.roninprogrammer.courierservice.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculator;
import com.roninprogrammer.courierservice.services.OfferService;

public class CourierServiceTest {

    @Test
    public void testDeliveryCostCalculation() {
        List<Parcel> testPackages = new ArrayList<>();
        testPackages.add(new Parcel("PKG1", 50, 30, "OFR001")); // No discount
        testPackages.add(new Parcel("PKG2", 75, 125, "OFR008")); // Invalid offer code
        testPackages.add(new Parcel("PKG3", 175, 100, "OFR003")); // Valid offer

        double baseDeliveryCost = 100;

        for (Parcel pkg : testPackages) {
            double cost = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
            pkg.setDiscount(OfferService.getDiscount(pkg, cost));
            pkg.setTotalCost(cost - pkg.getDiscount());
        }

        assertEquals(750.00, testPackages.get(0).getTotalCost(), 0.01, "PKG1 should have a total cost of 750.00");
        assertEquals(1475.00, testPackages.get(1).getTotalCost(), 0.01, "PKG2 should have a total cost of 1475.00");
        assertEquals(2350.00, testPackages.get(2).getTotalCost(), 0.01, "PKG3 should have a total cost of 2350.00");
    }

    @Test
    public void testDeliveryPrioritization() {
        List<Parcel> testPackages = new ArrayList<>();
        testPackages.add(new Parcel("PKG1", 50, 30, "OFR001"));
        testPackages.add(new Parcel("PKG2", 75, 125, "OFR008"));
        testPackages.add(new Parcel("PKG3", 175, 100, "OFR003"));
        testPackages.add(new Parcel("PKG4", 110, 60, "OFR002"));
        testPackages.add(new Parcel("PKG5", 155, 95, "NA"));

        List<Vehicle> testVehicles = new ArrayList<>();
        testVehicles.add(new Vehicle(70, 200));
        testVehicles.add(new Vehicle(80, 250));

        DeliveryCostCalculator.calculate(testPackages, testVehicles);

        assertEquals(3.98, testPackages.get(0).getEstimatedDeliveryTime(), 0.01);
        assertEquals(1.78, testPackages.get(1).getEstimatedDeliveryTime(), 0.01);
        assertEquals(1.42, testPackages.get(2).getEstimatedDeliveryTime(), 0.01);
        assertEquals(0.85, testPackages.get(3).getEstimatedDeliveryTime(), 0.01);
        assertEquals(4.19, testPackages.get(4).getEstimatedDeliveryTime(), 0.01);
    }
}
