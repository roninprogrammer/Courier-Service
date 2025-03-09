package com.roninprogrammer.courierservice.model;

public class Vehicle {
    double speed;
    double maxWeight;
    double availableAt;


    public Vehicle(double speed, double maxWeight) {
        this.speed = speed;
        this.maxWeight = maxWeight;
        this.availableAt = 0;
    }

    //getter
    public double getSpeed() {
        return speed;
    }


    public double getMaxWeight() {
        return maxWeight;
    }


    public double getAvailableAt() {
        return availableAt;
    }

    //setter
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setAvailableAt(double availableAt) {
        this.availableAt = availableAt;
    }


}
