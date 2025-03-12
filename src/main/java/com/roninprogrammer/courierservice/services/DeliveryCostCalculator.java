package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DeliveryCostCalculator {

    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles) {
        // Prioritize heavier packages first
        packages.sort(Comparator.comparingDouble(Parcel::getWeight).reversed());

        PriorityQueue<Vehicle> vehicleQueue = new PriorityQueue<>(Comparator.comparingDouble(Vehicle::getAvailableAt));

        for (Vehicle vehicle : vehicles) {
            vehicleQueue.add(vehicle);
        }

        for (Parcel pkg : packages) {
            Vehicle assignedVehicle = vehicleQueue.poll();

            if (assignedVehicle == null) {
                throw new IllegalStateException("No vehicles available for assignment.");
            }

            double deliveryTime = pkg.getDistance() / assignedVehicle.getSpeed();
            assignedVehicle.updateAvailability(deliveryTime);
            pkg.setEstimatedDeliveryTime(assignedVehicle.getAvailableAt());

            vehicleQueue.add(assignedVehicle);
        }
    }
}
