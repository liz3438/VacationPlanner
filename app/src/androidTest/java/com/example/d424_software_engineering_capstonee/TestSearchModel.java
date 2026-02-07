package com.example.d424_software_engineering_capstonee;

import org.junit.Test;
import com.example.d424_software_engineering_capstonee.models.Search;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestSearchModel {
    private Search tripSearch;
    private Search conferenceSearch;

    @Before
    public void setUp(){
        tripSearch = new Search(
                "TRIP",
                1,
                "Maldives",
                "Beach Trip",
                "07/04/2026 - 07/15/2026",
                "2000.0"
        );
        conferenceSearch = new Search(
                "CONFERENCE",
                5,
                "Scuba Conference",
                "Trip ID: 1",
                "07/04/2026 - 07/15/2026",
                ""

        );
    }
    @Test
    public void testSearchCreation(){
        assertEquals("TRIP", tripSearch.getType());
        assertEquals(1, tripSearch.getId());
        assertEquals("Beach Trip", tripSearch.getTitle());
        assertEquals("Maldives", tripSearch.getDetails());
    }

    @Test
    public void testTripSearchType(){
        assertEquals("TRIP", tripSearch.getType());
    }

    @Test
    public void testConferenceSearch(){
        assertEquals("CONFERENCE", conferenceSearch.getType());
    }

    @Test
    public void testSearchPrice(){
        assertNotNull(tripSearch.getPrice());
        assertFalse(tripSearch.getPrice().isEmpty());
    }



}
