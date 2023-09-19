package cs3500.pa04.play;

import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Obtains user input in the game.
 */
public class UserInput {
  private final int minBoardDimension = 6;
  private final int maxBoardDimension = 15;
  private final Scanner in;

  /**
   * Constructs a `UserInput` object.
   *
   * @param in the `Scanner` object used for reading user input
   */
  public UserInput(Scanner in) {
    this.in = in;
  }

  /**
   * Gets user input for shot coordinates.
   *
   * @param shotCount the number of shot coordinates to obtain
   * @return a list of user-input shot coordinates
   */
  public List<Coord> getUserShotCoordinates(int shotCount) {
    int[] input;
    List<Coord> shots = new ArrayList<>();
    System.out.println("Please Enter " + shotCount + " Shots:");
    for (int i = 0; i < shotCount; i++) {
      input = getUserInput();
      shots.add(new Coord(input[0], input[1]));
    }
    return shots;
  }

  /**
   * Gets user input for the board dimensions.
   *
   * @return an array containing the height and width of the board
   */
  public int[] getBoardDimension() {
    int[] input = new int[2];
    boolean isValidInput = false;
    while (!isValidInput) {
      System.out.println("Please enter a valid height and width below:");
      input = getUserInput();
      isValidInput = Arrays.stream(input).noneMatch(p -> (p < minBoardDimension)
          || (p > maxBoardDimension));
      if (!isValidInput) {
        System.out.println("Uh Oh! You've entered invalid dimensions. "
            + "Please remember that the height and width\n"
            + "of the game must be in the range (6, 10), inclusive. Try again!");
      }
    }
    return input;
  }

  /**
   * Gets user input for the ship fleet sizes.
   *
   * @return an array containing the sizes of the carrier, battleship, destroyer, and submarine
   */
  public int[] getShipFleetSize() {
    int[] input = new int[2];
    boolean isValidInput = false;
    while (!isValidInput) {
      System.out.println("Please enter your fleet in the order "
          + "[Carrier, Battleship, Destroyer, Submarine].");
      System.out.println("Remember, your fleet may not exceed size 8.");
      input = getUserInput();
      int fleetSize = Arrays.stream(input).sum();
      boolean fleetSizeZero = Arrays.stream(input).noneMatch(p -> p == 0);
      isValidInput = (fleetSizeZero && fleetSize <= 8);
      if (!isValidInput) {
        System.out.println("Uh Oh! You've entered invalid fleet sizes.");
      }
    }
    return input;
  }

  /**
   * Reads user input and converts it to an array of integers.
   *
   * @return an array of integers representing the user input
   */
  private int[] getUserInput() {
    String line = in.nextLine();
    String[] strInput = line.split(" ");
    int[] input = new int[strInput.length];
    for (int i = 0; i < strInput.length; i++) {
      input[i] = Integer.parseInt(strInput[i]);
    }
    return input;
  }
}
