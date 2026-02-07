package com.example.d424_software_engineering_capstonee;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import com.example.d424_software_engineering_capstonee.entities.Trip;


public class TripTest {

    private Trip trip;

    @Before
    public void setUp(){
        trip = new Trip(2000.0, "Beach Trip", "Hawaii Days Inn", "07/04/2026", "07/15/2026");
    }
    //Test trip creation
    @Test
    public void testCreateTrip(){
        assertEquals("Beach Trip", trip.getTripName());
        assertEquals(2000.0, trip.getPrice(), 0.01);
        assertEquals("Hawaii Days Inn", trip.getHotel());
        assertEquals("07/04/2026", trip.getStartDate());
        assertEquals("07/15/2026", trip.getEndDate());
    }


    @Test
    public void testSetTripName(){
        trip.setTripName("Mountain Trip");
        assertEquals("Mountain Trip",trip.getTripName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePrice(){
        trip.setPrice(-50.0);
    }
}
