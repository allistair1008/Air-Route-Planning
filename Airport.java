// --== CS400 File Header Information ==--
// Name: Aidan Godfrey
// Email: amgodfrey@wisc.edu
// Team: DC
// Role: Data Wrangler
// TA: Yelun Bao
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

/**
 * Class to define a node, which is our Airport to store the location, city, state, and airport
 * 
 * @author Aidan Godfrey
 */
public class Airport {
  private String cityName;
  private String airportName;
  private double latitude;
  private double longitude;
  private String state;

  /**
   * Creates an airport by setting all of the fields
   * 
   * @param cityName
   * @param airportName
   * @param latitude
   * @param longitude
   * @param state
   */
  public Airport(String cityName, String airportName, double latitude, double longitude,
      String state) {
    this.cityName = cityName;
    this.airportName = airportName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.state = state;
  }


  /**
   * Getter for the city name
   * 
   * @return city name as a string
   */
  public String getCityName() {
    return this.cityName;
  }


  /**
   * Getter for the airport name
   * 
   * @return airport name as a string
   */
  public String getAirportName() {
    return this.airportName;
  }

  /**
   * Getter for the latitude
   * 
   * @return latitude as a double
   */
  public double getLatitude() {
    return this.latitude;
  }

  /**
   * Getter for the longitude
   * 
   * @return longitude as a double
   */
  public double getLongitude() {
    return this.longitude;
  }

  /**
   * Getter for the state
   * 
   * @return state as a string
   */
  public String getState() {
    return this.state;
  }

  public String toString() {
    String airportString = "Name: " + airportName + " City: " + cityName + " State: " + state
        + " Latitude: " + latitude + " Longitude: " + longitude;
    return airportString;
  }
}
