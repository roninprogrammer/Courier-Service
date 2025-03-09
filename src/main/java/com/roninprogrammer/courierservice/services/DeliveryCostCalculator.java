package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DeliveryCostCalculator {
    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles, Scanner scanner, ExecutorService executor) {
        packages.sort(Comparator.comparingDouble(Parcel::getWeight).reversed());
        for (Parcel pkg : packages) {
            Vehicle assignedVehicle = vehicles.stream().min(Comparator.comparingDouble(Vehicle::getAvailableAt)).orElse(null);
            if (assignedVehicle != null) {
                double time = pkg.getDistance() / assignedVehicle.getSpeed();
                double roundedTime = Math.round((assignedVehicle.getAvailableAt() + time) * 100.0) / 100.0;
                assignedVehicle.setAvailableAt(roundedTime);
                pkg.setDeliveryTime(roundedTime);
            }
        }
    }
}

