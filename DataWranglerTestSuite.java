import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

// --== CS400 File Header Information ==--
// Name: Yuan Chen   
// Email: chen2243@wisc.edu
// Team: DC
// TA: Yelun
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class DataWranglerTestSuite {

   /**
    * Tests whether Airport object is constructed correctly.
    */
   @Test
   public void testAirportConstructor() {
       Airport ap = null;
       try {
           ap = new Airport("some city", "some airport", 0.1, 10000.0,
                 "some state");
       } catch (Exception e) {
           fail("Fail to construct airport object: " + e.getMessage());
       }
       assertEquals("some city", ap.getCityName());
       assertEquals("some airport", ap.getAirportName());
       assertEquals("some state", ap.getState());
       assertEquals(0.1, ap.getLatitude(), 0.001);
       assertEquals(10000.0, ap.getLongitude(), 0.001);
   }

   /**
    * Tests whether DataLoader correctly loads data from csv file
    */
   @Test
   public void testLoadData() {
       try {
           DataLoader dataLoader = new DataLoader();
           assertNotNull(dataLoader, "DataLoader construction failed");
           ArrayList<Airport> data = dataLoader.getAirports();
           for (int i = 0; i < data.size(); i++) {
               assertNotNull(data.get(i), "Data loaded contains null object");
           }
           assertEquals(data.size(), 155);
           assertEquals("Abilene", data.get(0).getCityName());
           assertEquals("Dyess Air Force Base" + 
                 "", data.get(0).getAirportName());
           assertEquals("TX", data.get(0).getState());
           assertEquals(32.42079926
, data.get(0).getLatitude(), 0.001);
           assertEquals(-99.854599
, data.get(0).getLongitude(), 0.001);           
       } catch (Exception e) {
           fail(e.getMessage());
       }
       
   }
}
