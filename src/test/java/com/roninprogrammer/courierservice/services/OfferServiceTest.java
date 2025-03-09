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

public class OfferServiceTest {
    private Parcel testParcel;
    
    @BeforeEach
    void setUp() {
        testParcel = new Parcel("PKG1", 10, 100, "OFR003");
    }
    
    @Test
    void testOfferServiceDiscount() {
        double cost = 100 + (testParcel.getWeight() * 10) + (testParcel.getDistance() * 5);
        double discount = OfferService.getDiscount(testParcel, cost);
        assertEquals(35.0, discount, 0.01, "Discount should be 5% of 700");
    }
    
    @Test
    void testInvalidOfferCode() {
        Parcel invalidParcel = new Parcel("PKG2", 15, 50, "INVALID");
        double cost = 100 + (invalidParcel.getWeight() * 10) + (invalidParcel.getDistance() * 5);
        double discount = OfferService.getDiscount(invalidParcel, cost);
        assertEquals(0.0, discount, "Invalid offer codes should give 0 discount");
    }
}