// --== CS400 File Header Information ==--
// Name: Yuan Chen
// Email: chen2243@wisc.edu
// Team: DC
// TA: Yelun
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BackEndFrontEndTestSuite {
  PathFinder testFinder;

  @Before
  public void setup() {
    testFinder = new PathFinder();
  }

  /**
   * This method tests if the findDistance method return the correct value of distance between two
   * airports
   */
  @Test
  public void testFindDistance() {
    Airport a1 = new Airport("c1", "a1", 10.0, 10.0, "s1");
    Airport a2 = new Airport("c2", "a2", 20.0, 20.0, "s2");
    // pass if difference is within 10km
    assertEquals(1554.0, testFinder.distanceBetween(a1, a2), 10.0,
        "Calculation of distance is not correct!");
  }

  /**
   * This method tests if the getAirport return the correct airport also tests if getAirportName
   * return the correct name of an airport
   */
  @Test
  public void testGetAirport() {
    assertEquals(testFinder.getAirport("New York", "NY").getAirportName(),
        "John F Kennedy International Airport",
        "getAirport() didn't return the airport correctly!");
    assertEquals(testFinder.getAirport("Tulsa", "OK").getAirportName(),
        "Tulsa International Airport", "getAirport() didn't return the airport correctly!");
    assertEquals(testFinder.getAirport("Wichita Falls", "TX").getAirportName(),
        "Sheppard Air Force Base-Wichita Falls Municipal Airport",
        "getAirport() didn't return the airport correctly!");
    assertEquals(testFinder.getAirport("11", "22"), null, "getAirport() should return null!");
  }

  /**
   * This method tests if the findShortestPath() method can return the shortest path
   */
  @Test
  public void testFindShortestPath() {
    ArrayList<Airport> path1 = testFinder.findShortestPath("New York", "NY", "Miami", "FL", 1000);
    assertEquals(path1.get(0).getCityName(), "New York");
    assertEquals(path1.get(1).getCityName(), "Goldsboro");
    assertEquals(path1.get(2).getCityName(), "West Palm Beach");
    assertEquals(path1.get(3).getCityName(), "Miami");
    ArrayList<Airport> path2 = testFinder.findShortestPath("New York", "NY", "Miami", "FL", 2000);
    assertEquals(path2.get(0).getCityName(), "New York");
    assertEquals(path2.get(1).getCityName(), "Miami");
  }

  /**
   * This method tests if the contains method return the correct result
   */
  @Test
  public void testContains() {
    assertTrue(testFinder.contains("Toledo", "OH"));
    assertTrue(!testFinder.contains("somecity", "nowhere"));
  }

  @Test
  public void test() {
    String s1 = "new york";
    assertEquals("New York", CommandLineDriver.formatCity(s1));
    String s2 = "Dallas";
    assertEquals("Dallas", CommandLineDriver.formatCity(s2));
    String s3 = "MADISON";
    assertEquals("Madison", CommandLineDriver.formatCity(s3));
  }

}
