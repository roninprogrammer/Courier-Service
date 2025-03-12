package com.roninprogrammer.courierservice.services;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculator;
import com.roninprogrammer.courierservice.services.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OfferServiceTest {

    @Test
    void testValidOfferCode() {
        Parcel parcel = new Parcel("PKG1", 100, 150, "OFR001");
        double baseCost = 100;
        double expectedCost = baseCost + (100 * 10) + (150 * 5); // 100 + 1000 + 750 = 1850
        double discount = OfferService.getDiscount(parcel, expectedCost);

        assertEquals(185.00, discount, "OFR001 should give a 10% discount");
    }

    @Test
    void testInvalidOfferCode() {
        Parcel parcel = new Parcel("PKG2", 50, 50, "INVALID");
        double baseCost = 100;
        double expectedCost = baseCost + (50 * 10) + (50 * 5); // 100 + 500 + 250 = 850
        double discount = OfferService.getDiscount(parcel, expectedCost);

        assertEquals(0.00, discount, "Invalid offer code should return 0 discount");
    }

    @Test
    void testOfferNotApplicable() {
        Parcel parcel = new Parcel("PKG3", 250, 300, "OFR002"); // Out of valid range
        double baseCost = 100;
        double expectedCost = baseCost + (250 * 10) + (300 * 5); // 100 + 2500 + 1500 = 4100
        double discount = OfferService.getDiscount(parcel, expectedCost);

        assertEquals(0.00, discount, "OFR002 should not apply as weight/distance is invalid");
    }
}
