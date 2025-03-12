package com.roninprogrammer.courierservice.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final double speed;
    private final double maxWeight;
    private double availableAt;

    public Vehicle(double speed, double maxWeight) {
        if (speed <= 0 || maxWeight <= 0) {
            throw new IllegalArgumentException("Speed and Max Weight must be positive values.");
        }

        this.speed = speed;
        this.maxWeight = maxWeight;
        this.availableAt = 0;
    }

    public double getSpeed() { return speed; }
    public double getMaxWeight() { return maxWeight; }
    public double getAvailableAt() { return availableAt; }

    public void setAvailableAt(double availableAt) {
        this.availableAt = availableAt;
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
