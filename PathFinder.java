// --== CS400 File Header Information ==--
// Name: Alexander Peseckis
// Email: peseckis@wisc.edu
// Team: DC
// Role: Back End Developer
// TA: Yulan BAO
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.lang.Math;
import java.util.NoSuchElementException;


/**
 * A class that holds all Airports, their locations, and the distances between
 *  them. This class will allow the user to find the shortest path between two
 *  airports given that the airplane they intend to use can only fly so far.
 */
public class PathFinder {
    protected HashMap<String, Airport> hashMapOfAirports; // An arrayList of all the Airports
    protected ArrayList<Airport> arrayListOfAirports;

    final double EARTHS_RADIUS = 6371; // in km

    /**
     * Constructs a new PathFinder Class.
     */
    public PathFinder() {
        arrayListOfAirports = (new DataLoader()).getAirports();
        hashMapOfAirports = new HashMap<>();

        for (Airport airport: arrayListOfAirports) {
            String[] cityNames = airport.getCityName().split("/");
            String[] stateNames = airport.getState().split("/");

            for (String cityName: cityNames) {
                for (String stateName: stateNames) {
                    hashMapOfAirports.put(cityName + stateName, airport);
                }
            }
        }
    }

    /**
     * Finds and returns the distance (in km) between the two given airports.
     *  This distance is found using the Haversine formula.
     * @param air1 the airport you are coming from
     * @param air2 the airport you are going to
     * @return the distance in km between the given airports
     */
    protected double distanceBetween(Airport air1, Airport air2) {
        // Get latitiude and longitude for airport 1
        double lat1 = air1.getLatitude();
        double lon1 = air1.getLongitude();

        // Get latitiude and longitude for airport 2
        double lat2 = air2.getLatitude();
        double lon2 = air2.getLongitude();

        // Calculate the distance between the two airports

        double a = Math.sin(Math.toRadians(lat2 - lat1) / 2) * Math.sin(Math.toRadians(lat2 - lat1) / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(Math.toRadians(lon2 - lon1) / 2) * Math.sin(Math.toRadians(lon2 - lon1) / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = Math.sqrt(Math.pow(EARTHS_RADIUS * c, 2)); // Distance in km
        return distance;
    }

    /**
     * Helper method that returns an airport object corresponding to the given
     *  airportCity and airportState.
     * @param airportCity city of the airport
     * @param airportState state of the airport
     * @return either the airport with the given city and state or null if it could
     *  not be found.
     */
    protected Airport getAirport(String airportCity, String airportState) {
        if (hashMapOfAirports.containsKey(airportCity + airportState))
            return hashMapOfAirports.get(airportCity + airportState);
        return null;
    }

    /**
     * Returns the shortest path between the two given airports.
     *
     * In the returned ArrayList, for every airport at index n, index n + 1 contains the next
     *    airport on the way to the destination airport (if index n + 1 is null, then index n
     *    contains the destination airport)
     * @param startingAirportCity the name of the starting airport's city
     * @param startingAirportState the name of the starting airport's state
     * @param destinationAirportCity the name of the distination airport's city
     * @param destinationAirportState the name of the distination airport's state
     * @param maxDistancePlaneCanFly the maximum number of kilometer that the plane can fly
     * @return null if there was no flight path between the given airport and destination airport,
     *  an ArrayList of the path from the startingAirportCity to the destinationAirportState.
     */
    public ArrayList<Airport> findShortestPath(String startingAirportCity, String startingAirportState,
            String destinationAirportCity, String destinationAirportState, int maxDistancePlaneCanFly) {
        CS400Graph<Airport> airportGraph = new CS400Graph<>();

        // Insert all the airports into the graph as Vertexes
        for (Airport airport: arrayListOfAirports)
            airportGraph.insertVertex(airport);

        // For every airport we're focused on
        for (Airport focusedAirport: arrayListOfAirports) {

            // Create an edge between the airport we're focused on and every other
            //      airport in the list that the user can fly two with their plane.
            for (Airport currAirport: arrayListOfAirports) {

                // No flight path between the focused airport and itself so continue
                if (currAirport == focusedAirport) continue;

                // Calculate the distance between the focusedAirport and currAirport
                int distance = (int)(distanceBetween(focusedAirport, currAirport) * 1000); // in meters so that it is more accurate

                // Only insert edges that are shorter than or equal to maxDistancePlaneCanFly
                if (distance <= (maxDistancePlaneCanFly * 1000)) { // both converted into meters so that it is more accurate
                    airportGraph.insertEdge(focusedAirport, currAirport, distance);
                }
            }
        }

        Airport air1 = getAirport(startingAirportCity, startingAirportState);
        Airport air2 = getAirport(destinationAirportCity, destinationAirportState);
        try {
            List<Airport> path = airportGraph.shortestPath(air1, air2);
            ArrayList<Airport> arrayListPath = new ArrayList<Airport>(path); // converts List into ArrayList
            return arrayListPath;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Returns true if the PathFinder class contains an airport with the given string,
     *  false otherwise.
     * @param airportCity city of the airport
     * @param airportState state of the airport
     * @return returns true if the PathFinder class contains an airport with the
     *  given string, false otherwise.
     */
    public boolean contains(String airportCity, String airportState) {
        if (getAirport(airportCity, airportState) == null) return false;
        return true;
    }

    /**
     * Returns the name of the large airport in the given city and state.
     * @param airportCity city of the airport
     * @param airportState state of the airport
     * @return the name of the airport or null if it is not in the PathFinder
     */
    public String getAirportName(String airportCity, String airportState) {
        Airport airport = getAirport(airportCity, airportState);
        if (airport == null) return null;
        return airport.getAirportName();
    }
}
