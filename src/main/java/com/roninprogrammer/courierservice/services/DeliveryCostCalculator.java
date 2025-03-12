package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import java.util.*;

public class DeliveryCostCalculator {

    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles) {
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
            
            //Assign the package to the vehicle with the earliest available time
            double deliveryTime = pkg.getDistance() / assignedVehicle.getSpeed();
            deliveryTime = Math.round(deliveryTime * 100.0) / 100.0; // Round to 2 decimal places

            assignedVehicle.setAvailableAt(assignedVehicle.getAvailableAt() + (2 * deliveryTime));
            pkg.setEstimatedDeliveryTime(deliveryTime);

            vehicleQueue.add(assignedVehicle);
        }
    }
}
