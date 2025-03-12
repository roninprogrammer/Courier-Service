package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;

public class OfferService {

    public static double getDiscount(Parcel pkg, double cost) {
        if (pkg == null || pkg.getOfferCode() == null) {
            return 0.00;
        }
        double discountRate = OfferDetails.getDiscount(pkg.getOfferCode(), pkg.getWeight(), pkg.getDistance());
        double discount = cost * discountRate;
        return Math.round(discount * 100.0) / 100.0;
    }
}