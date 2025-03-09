package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;

import java.util.Comparator;
import java.util.List;

public class DeliveryCostCalculator {
    public static void calculate(List<Parcel> packages, List<Vehicle> vehicles) {
        packages.sort(Comparator.comparingDouble(Parcel::getWeight).reversed());
        for (Parcel pkg : packages) {
            Vehicle assignedVehicle = vehicles.stream().min(Comparator.comparingDouble(v -> v.getAvailableAt())).orElse(null);
            if (assignedVehicle != null) {
                double time = pkg.getDistance() / assignedVehicle.getSpeed();
                pkg.setDeliveryTime(assignedVehicle.getAvailableAt() + time);
                assignedVehicle.setAvailableAt(assignedVehicle.getAvailableAt() + time);
            }
        }
    }
}
