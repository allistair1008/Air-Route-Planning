// --== CS400 File Header Information ==--
// Name: Aidan Godfrey
// Email: amgodfrey@wisc.edu
// Team: DC
// Role: Data Wrangler
// TA: Yelun Bao
// Lecturer: Florian Heimerl
// Notes to Grader: The original data was taken from "https://datahub.io/core/airport-codes" and I
// processed the data
// using pandas/python, if you would like to see the notebook for the processing I can upload it to
// github

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to construct the arraylist of airports on construction for the backend to use
 */
public class DataLoader {
  private final String CSVPATH = "usAirportsTotal.csv";
  private ArrayList<Airport> airports; // Field that contains all the airports combined

  /**
   * Loads the data from the given csv to use There will only be 155 entries after I filtered the
   * duplicates
   */
  public DataLoader() {

    ArrayList<Airport> airports = new ArrayList<Airport>();
    BufferedReader br = null;
    // Attempt to read it with a buffered reader

    try {
      br = new BufferedReader(new FileReader(CSVPATH));
      String line = "";
      int lineCount = 0;
      // While there exists lines of the csv continue
      while ((line = br.readLine()) != null) {
        // Skip the Header
        if (lineCount == 0) {
          lineCount = 1;
          continue;
        }
        String[] data = line.split(",");

        // The order to insert is name of the city, airport, lat, long and state
        Airport newAirport = new Airport(data[1], data[0], Double.parseDouble(data[3]),
            Double.parseDouble(data[4]), data[2]);
        airports.add(newAirport);
      }
    }

    catch (IOException e) {
      System.out.println(e.getMessage());
    }

    finally {
      if (br != null) {
        try {
          br.close();
        } // If it cannot close, this is needed.
        catch (IOException io) {
          System.out.println(io.getMessage());
        }
      }
      // Assign the variable to the ArrayList<Airport>
      this.airports = airports;
    }
  }

  /**
   * Getter for all the airports that the back end should use
   * 
   * @return airports as an ArrayList<Airport>
   */
  public ArrayList<Airport> getAirports() {
    return this.airports;
  }

  // Redacted for personal use
  // public static void main(String[] args){
  // DataLoader data = new DataLoader();
  // data.testData(data.getAirports());
  // }

  /**
   * Test method to ensure the size is constant and the csv is read correctly
   * 
   * @param data of the airports
   */
  private void testData(ArrayList<Airport> data) {
    if (data == null) {
      System.out.println("The Data is null");
    }
    if (data.size() != 155) {
      System.out.println(data.size());
      System.out.println("Size does not equal 155");
    }
    Airport airport1 = data.get(0);
    if (!airport1.getCityName().equals("Abilene") || (airport1.getLatitude() != 32.4207992554)) {
      System.out.println("The first airport is in the worng spot");
    }

    Airport airport154 = data.get(154);
    if ((!airport154.getCityName().equals("Wichita Falls"))
        || (airport154.getLatitude() != 33.9888)) {
      System.out.println("The last airport is in the worng spot");
    }

    if ((!airport154.getAirportName()
        .equals("Sheppard Air Force Base-Wichita Falls Municipal Airport"))) {
      System.out.println("Wrong name");
    }
    if (airport154.getLongitude() != -98.491898 || (!airport154.getState().equals("TX"))) {
      System.out.println("Wrong Longitude or Wrong State for airport 154");
    }

  }


}
