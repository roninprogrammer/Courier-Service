package com.roninprogrammer.courierservice.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final double speed;
    private final double maxWeight;
    private double availableAt;
    private final List<Parcel> assignedParcels;

    public Vehicle(double speed, double maxWeight) {
        if (speed <= 0 || maxWeight <= 0) {
            throw new IllegalArgumentException("Speed and Max Weight must be positive values.");
        }

        this.speed = speed;
        this.maxWeight = maxWeight;
        this.availableAt = 0;
        this.assignedParcels = new ArrayList<>();
    }

    
    public double getSpeed() { return speed; }
    public double getMaxWeight() { return maxWeight; }
    public double getAvailableAt() { return availableAt; }
    public List<Parcel> getAssignedParcels() { return assignedParcels; }

    public void setAvailableAt(double availableAt) {
        this.availableAt = availableAt;
    }

    
    public boolean assignParcel(Parcel parcel) {
        double totalWeight = assignedParcels.stream().mapToDouble(Parcel::getWeight).sum();
        if (totalWeight + parcel.getWeight() > maxWeight) {
            return false; 
        }
        assignedParcels.add(parcel);
        return true;
    }

    public void updateAvailability(double deliveryTime) {
        availableAt += 2 * deliveryTime; 
    }

    @Override
    public String toString() {
        return String.format("Vehicle (Speed: %.2f km/hr, Max Load: %.2f kg, Available At: %.2f hrs)", 
        speed, maxWeight, availableAt);
    }
}