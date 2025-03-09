package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.utils.OfferCodes;

import java.util.Map;

public class OfferDetails {
    public static final Map<String, Double> DISCOUNTS = Map.of(
            OfferCodes.OFR001, 0.10,
            OfferCodes.OFR002, 0.07,
            OfferCodes.OFR003, 0.05
    );

    public static boolean isApplicable(String code, double weight, double distance) {
        return switch (code) {
            case OfferCodes.OFR001 -> distance < 200 && weight >= 70 && weight <= 200;
            case OfferCodes.OFR002 -> distance >= 50 && distance <= 150 && weight >= 100 && weight <= 250;
            case OfferCodes.OFR003 -> distance >= 50 && distance <= 250 && weight >= 10 && weight <= 150;
            default -> false;
        };
    }
}
