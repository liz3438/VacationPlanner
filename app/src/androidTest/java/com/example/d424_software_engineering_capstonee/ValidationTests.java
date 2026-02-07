package com.example.d424_software_engineering_capstonee;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ValidationTests {

    private boolean isDateValid(String date){
        if(date == null || date.isEmpty()){
            return false;

        }else {
            String pattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$";
            return date.matches(pattern);
        }

    }
    @Test
    public void testDateValid(){
        assertTrue(isDateValid("06/15/2026"));
        assertTrue(isDateValid("01/24/2026"));
        assertTrue(isDateValid("12/31/2027"));
    }

    @Test
    public void invalidTestDate(){
        assertFalse(isDateValid("2/4/2026"));
        assertFalse(isDateValid("15/09/2027"));
        assertFalse(isDateValid("2027/08/09"));
        assertFalse(isDateValid("06-05-2026"));
    }

    @Test
    public void emptyTestDate(){
        assertFalse(isDateValid(""));
    }

    @Test
    public void nullDate(){
        assertFalse(isDateValid(null));
    }
}
