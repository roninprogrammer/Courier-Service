package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import java.util.*;

public class DeliveryCostCalculator {
    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles) {
        // Sort packages descending by weight
        packages.sort(Comparator.comparingDouble(Parcel::getWeight).reversed());
    
        PriorityQueue<Vehicle> vehicleQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Vehicle::getAvailableAt)
                      .thenComparing(Comparator.comparingDouble(Vehicle::getSpeed).reversed())
        );
        vehicleQueue.addAll(vehicles);
    
        List<Parcel> pendingPackages = new ArrayList<>(packages);
    
        while (!pendingPackages.isEmpty()) {
            Vehicle vehicle = vehicleQueue.poll();
            
            double currentLoad = 0;
            List<Parcel> currentShipment = new ArrayList<>();
    
            Iterator<Parcel> iterator = pendingPackages.iterator();
            while (iterator.hasNext()) {
                Parcel pkg = iterator.next();
                if (currentLoad + pkg.getWeight() <= vehicle.getMaxWeight()) {
                    currentShipment.add(pkg);
                    currentLoad += pkg.getWeight();
                    iterator.remove();
                }
            }
    
            if (currentShipment.isEmpty()) {
                // No package fits, skip vehicle this round
                vehicleQueue.add(vehicle);
                continue;
            }
    
            // Calculate delivery time (farthest package determines round-trip time)
            double farthestDistance = currentShipment.stream()
                                        .mapToDouble(Parcel::getDistance)
                                        .max().orElse(0);
            double deliveryTime = farthestDistance / vehicle.getSpeed();
            deliveryTime = Math.round(deliveryTime * 100.0) / 100.0;
    
            // Set estimated delivery time individually (vehicle availability + one-way delivery time)
            for (Parcel pkg : currentShipment) {
                double pkgDeliveryTime = vehicle.getAvailableAt() + (pkg.getDistance() / vehicle.getSpeed());
                pkgDeliveryTime = Math.round(pkgDeliveryTime * 100.0) / 100.0;
                pkg.setEstimatedDeliveryTime(pkgDeliveryTime);
            }
    
            // Update vehicle availability (round-trip)
            vehicle.setAvailableAt(vehicle.getAvailableAt() + (2 * deliveryTime));
            vehicleQueue.add(vehicle);
        }

    }
}