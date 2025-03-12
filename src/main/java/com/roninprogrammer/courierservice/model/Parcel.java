package com.roninprogrammer.courierservice.model;

import com.roninprogrammer.courierservice.services.OfferDetails;

public class Parcel {
    private final String id;
    private final double weight;
    private final double distance;
    private final String offerCode;
    private double discount;
    private double totalCost;
    private double estimatedDeliveryTime;

    public Parcel(String id, double weight, double distance, String offerCode) {
        if (weight <= 0 || distance <= 0) {
            throw new IllegalArgumentException("Weight and Distance must be positive values.");
        }

        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
        this.discount = 0;
        this.totalCost = 0;
        this.estimatedDeliveryTime = 0;
    }

    public String getId() { return id; }
    public double getWeight() { return weight; }
    public double getDistance() { return distance; }
    public String getOfferCode() { return offerCode; }
    public double getDiscount() { return discount; }
    public double getTotalCost() { return totalCost; }
    public double getEstimatedDeliveryTime() { return estimatedDeliveryTime; }

    public void calculateDiscount(double baseDeliveryCost) {
        double cost = baseDeliveryCost + (weight * 10) + (distance * 5);
        this.discount = OfferDetails.getDiscount(offerCode, weight, distance) * cost;
        this.totalCost = cost - discount;
    }

    public void setEstimatedDeliveryTime(double time) {
        this.estimatedDeliveryTime = Math.round(time * 100.0) / 100.0; // Rounds to 2 decimal places
    }

    @Override
    public String toString() {
        return String.format("Parcel %s (%.2fkg, %.2fkm, Offer: %s, Cost: %.2f, Time: %.2f hrs)", 
                id, weight, distance, offerCode, totalCost, estimatedDeliveryTime);
    }
}
