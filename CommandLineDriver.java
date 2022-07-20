// --== CS400 File Header Information ==--
// Name: Allistair Mascarenhas
// Email: anmascarenha@wisc.edu
// Team: DC
// Role: Front End Developer 2
// TA: Yelun Bao
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the driver for the Air Route Planning Tool application. Once the main method is
 * run, it prompts the user for input to choose from different commands: 
 *      1) Get the large airport in a city 
 *      2) Get the shortest path to travel from a city to another 
 *      3) Exit the application
 * 
 * The main method runs indefinitely until the user inputs "3" which then exits the program.
 * 
 * @author Allistair
 */
public class CommandLineDriver {


  private final static String WELCOME_MESSAGE =
      "\n========= WELCOME to the Air Route Planning Tool! =========\n"
          + "With this application, you can search for"
          + " the shortest routes to fly from the large airport in one city"
          + " to another in the U.S.\n";


  private final static String INVALID_COMMAND = "\nThe command you just entered was invalid.\n"
      + "Please pick a number from 1 to 3 for its corresponding command.\n";

  private final static String MENU =
      "\n====================== COMMAND MENU ========================\n"
          + "1) Display the name of the large airport in a city\n"
          + "2) Find the shortest path to fly from one city to another\n"
          + "3) Exit the application\n" + "Please enter a command:\n";

  private final static String EXIT_COMMAND =
      "\n=========== Thank you for using our application! ===========\n";

  /**
   * Helper method to capitalize each word in a sentence. Used to format user input when they input
   * a city name.
   * 
   * @param sentence - name of the city
   * @return name of the city with each word capitalized
   */
  protected static String formatCity(String sentence) {
    Scanner sc = new Scanner(sentence);
    String capitalized = "";
    while (sc.hasNext()) {
      String word = sc.next().toLowerCase();
      capitalized += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
    }
    sc.close();
    return (capitalized.trim());
  }

  /**
   * Helper method to check that the user input for state follows the correct format (Capitalized 2
   * letter abbreviation of each state).
   * 
   * @param state - String of user input
   * @return correctly formatted String of state.
   */
  protected static String formatState(String state) {
    if (state.length() != 2) {
      return null;
    } else {
      return state.toUpperCase();
    }
  }

  /**
   * Method that carries out command 1) by taking in input from the user about city and state and
   * return the largest airport from that place.
   * 
   * @param pf   - PathFinder object contains the graph of airports and methods to find the shortest
   *             path
   * @param scnr - Scanner object used to take in user input
   */
  protected static void commandOne(PathFinder pf, Scanner scnr) {
    System.out.println("Enter the name of a city: ");
    String city = scnr.nextLine();

    city = formatCity(city);

    if (city.length() <= 0) {
      System.out.println("\nERROR: Please enter a valid city name.");
      return;
    }

    System.out.println("Enter the name of the state that the city is in as a two letter "
        + "abbreviation. Example (Wisconsin would be WI): ");
    String state = scnr.nextLine();
    state = formatState(state);

    if (state == null) {
      System.out
          .println("\nERROR: Please enter the state as a two letter abbreviation shown above.");
      return;
    }

    if (!pf.contains(city, state)) {
      System.out.println("\nThere are no large airports in this city.");
      return;
    }

    String airport = pf.getAirportName(city, state);
    System.out.println("\nThe largest airport in " + city + ", " + state + " is " + airport + ".");
  }

  /**
   * Method that carries out command 2) by taking in input from the user about starting and
   * destination city and state and the maximum distance the plane can fly. It then prints out the
   * shortest path of airports one would need to take to go from the starting city to the
   * destination city.
   * 
   * @param pf   - PathFinder object contains the graph of airports and methods to find the shortest
   *             path
   * @param scnr - Scanner object used to take in user input
   */
  protected static void commandTwo(PathFinder pf, Scanner scnr) {
    System.out.println("\n==== STARTING CITY AND STATE ====");
    System.out.println("Enter the name of the city to start at: ");
    String startCity = scnr.nextLine();

    System.out.println("Enter the name of the state that the starting city is in as a "
        + "two letter abbreviation. Example (Wisconsin would be WI): ");
    String startState = scnr.nextLine();

    startCity = formatCity(startCity);
    startState = formatState(startState);

    if (startCity.length() <= 0 || startState == null) {
      System.out
          .println("\nERROR: Please enter a valid starting city and state in the right format.");
      return;
    }

    if (!pf.contains(startCity, startState)) {
      System.out.println("\nThere are no large airports in the starting city.");
      return;
    }

    System.out.println("\n=== DESTINATION CITY AND STATE ===");
    System.out.println("Enter the name of the destination city: ");
    String destCity = scnr.nextLine();

    System.out.println("Enter the name of the state that the destination city is in as a "
        + "two letter abbreviation. Example (Wisconsin would be WI): ");
    String destState = scnr.nextLine();

    destCity = formatCity(destCity);
    destState = formatState(destState);

    if (destCity.length() <= 0 || destState == null) {
      System.out
          .println("\nERROR: Please enter a valid destination city and state in the right format.");
      return;
    }

    if (!pf.contains(destCity, destState)) {
      System.out.println("\nThere are no large airports in the destination city.");
      return;
    }

    System.out.println(
        "\nEnter the maximum distance your airplane could travel without refueling at an airport "
            + "(as a positive integer rounded to the nearest kilometer): ");
    int maxDistance = 0;
    try {
      maxDistance = Integer.parseInt(scnr.nextLine());
    } catch (NumberFormatException e) {
      System.out.print("\nERROR: Please enter distance as a whole number.");
      return;
    }

    if (maxDistance < 0) {
      System.out.println("\nERROR: The distance has to be a positive number.");
      return;
    }

    if (maxDistance > 40075) {
      System.out.println("\nERROR: The distance is greater than the circumference of the Earth!");
      return;
    }


    ArrayList<Airport> shortestPath =
        pf.findShortestPath(startCity, startState, destCity, destState, maxDistance);

    if (shortestPath == null) {
      System.out.println("\nFor the max distance your airplane could travel, "
          + "there is no viable path to reach the destination.");
      return;
    }

    System.out.println("");
    for (int i = 0; i < shortestPath.size(); i++) {
      Airport curr = shortestPath.get(i);
      System.out.print("[" + curr.getAirportName() + " - " + "(" + curr.getCityName() + ", "
          + curr.getState() + ")]");
      if (i < shortestPath.size() - 1)
        System.out.print(" -> ");
    }
    System.out.println("");
  }

  /**
   * The main method drives the whole Air Route Planning Tool Application, by indefinitely taking in
   * user input and then calling appropriate methods which then make calls to the Back End to get
   * and display the relevant information.
   * 
   * @param args - not used in this class
   */
  public static void main(String[] args) {
    // call made to BackEnd class which calls DataWrangler methods to load in all the data and
    // create a graph
    PathFinder pf = new PathFinder();

    System.out.print(WELCOME_MESSAGE);

    // Scanner object used to take in user input from command line
    Scanner scnr = new Scanner(System.in);

    // number of commands by user
    boolean exit = false;

    while (!exit) {
      char command;

      // while loop to get valid input for command
      while (true) {

        System.out.print(MENU);
        String input = scnr.nextLine();

        if (input.length() >= 1) {
          command = input.charAt(0);
          if (command >= '1' && command <= '3') {
            break;
          }
        }
        System.out.print(INVALID_COMMAND);
      }

      switch (command) {
        case '1':
          commandOne(pf, scnr);
          break;

        case '2':
          commandTwo(pf, scnr);
          break;

        case '3':
          exit = true;
          System.out.print(EXIT_COMMAND);
          scnr.close();
          break;

        default:
          System.out.print(INVALID_COMMAND);
          break;
      }
      System.out.println("");
    }
  }

}
