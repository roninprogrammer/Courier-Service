package com.roninprogrammer.courierservice.model;

public class Parcel {
    private String id;
    private double weight;
    private double distance;
    private String offerCode;
    private double discount;
    private double totalCost;
    private double deliveryTime;

    public Parcel(String id, double weight, double distance, String offerCode) {
        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
    }

    // Getters
    public String getId() { return id; }
    public double getWeight() { return weight; }
    public double getDistance() { return distance; }
    public String getOfferCode() { return offerCode; }
    public double getDiscount() { return discount; }
    public double getTotalCost() { return totalCost; }
    public double getDeliveryTime() { return deliveryTime; }

    // Setters
    public void setDiscount(double discount) { this.discount = discount; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setDeliveryTime(double deliveryTime) { this.deliveryTime = deliveryTime; }

    @Override
    public String toString() {
        return String.format("%s (Weight: %.2f kg, Distance: %.2f km, Offer Code: %s)", 
                id, weight, distance, offerCode);
    }

}
