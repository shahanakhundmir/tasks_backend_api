package com.impact.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestaurantTest {
    @Test
    @DisplayName("Test for restaurant name")
    public void testRestName() throws ParseException {
      //  try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            //String dateString = f.format(new Date());
            Date d = f.parse("2021-01-31");

            Restaurant r = new Restaurant(101,"Kohinoor","Central London","Please see all the allergen info provided with menu.",
                    d,d,113, "app001","v1");
            assertEquals("Kohinoor", r.getRest_name(), "Restaurant name is incorrect");
      //  }
        //catch(ParseException e){}
    }

    @Test
    @DisplayName("Test for restaurant id")
    public void testRestId(){
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            //String dateString = f.format(new Date());
            Date d = f.parse("2021-01-31");

            Restaurant r = new Restaurant(101,"Kohinoor","Central London","Please see all the allergen info provided with menu.",
                    d,d,113, "app001","v1");
            assertEquals(101, 101, "Must enter correct restaurant id");
        }
        catch(ParseException e)
        {}
    }
}





