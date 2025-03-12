package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.utils.OfferCodes;

import java.util.HashMap;
import java.util.Map;

public class OfferDetails {

    private static final class Offer {
        double discount;
        double minWeight, maxWeight;
        double minDistance, maxDistance;

        Offer(double discount, double minWeight, double maxWeight, double minDistance, double maxDistance) {
            this.discount = discount;
            this.minWeight = minWeight;
            this.maxWeight = maxWeight;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
        }

        boolean isApplicable(double weight, double distance) {
            return weight >= minWeight && weight <= maxWeight && distance >= minDistance && distance <= maxDistance;
        }
    }

    private static final Map<String, Offer> OFFERS = new HashMap<>();

    static {
        OFFERS.put(OfferCodes.OFR001, new Offer(0.10, 70, 200, 0, 200));
        OFFERS.put(OfferCodes.OFR002, new Offer(0.07, 100, 250, 50, 150));
        OFFERS.put(OfferCodes.OFR003, new Offer(0.05, 10, 150, 50, 250));
    }

    public static double getDiscount(String code, double weight, double distance) {
        if (code == null || !OFFERS.containsKey(code)) return 0.0;
        Offer offer = OFFERS.get(code);
        return offer.isApplicable(weight, distance) ? offer.discount : 0.0;
    }
}
