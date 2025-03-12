package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import java.util.*;

public class DeliveryCostCalculator {

    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles) {
        packages.sort(Comparator.comparingDouble(Parcel::getWeight).reversed());

        PriorityQueue<Vehicle> vehicleQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Vehicle::getAvailableAt)
                      .thenComparingDouble(Vehicle::getSpeed).reversed()
        );
        vehicleQueue.addAll(vehicles);

        for (Parcel pkg : packages) {
            Vehicle assignedVehicle = vehicleQueue.poll();

            if (assignedVehicle == null) {
                throw new IllegalStateException("No vehicles available for assignment.");
            }
            
            double deliveryTime = pkg.getDistance() / assignedVehicle.getSpeed();
            deliveryTime = Math.round(deliveryTime * 100.0) / 100.0;
    

            double estimatedDeliveryTime = assignedVehicle.getAvailableAt() + deliveryTime;
            estimatedDeliveryTime = Math.round(estimatedDeliveryTime * 100.0) / 100.0;
    
            pkg.setEstimatedDeliveryTime(estimatedDeliveryTime);
    
         
            assignedVehicle.setAvailableAt(assignedVehicle.getAvailableAt() + (2 * deliveryTime));
    
            vehicleQueue.add(assignedVehicle);
        }
    }
}
