package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;

public class OfferService {
    public static double getDiscount(Parcel pkg, double cost) {
        return (OfferDetails.DISCOUNTS.containsKey(pkg.getOfferCode()) && OfferDetails.isApplicable(pkg.getOfferCode(), pkg.getWeight(), pkg.getDistance()))
                ? cost * OfferDetails.DISCOUNTS.get(pkg.getOfferCode()) : 0;
    }
}
