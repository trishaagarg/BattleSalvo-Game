package cs3500.pa04.play;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Responsible for placing ships on the game board.
 */
public class ShipPlacement {

  /**
   * Places the ships on the game board.
   *
   * @param ships the list of ships to be placed
   * @param width the width of the game board
   * @param height the height of the game board
   */
  public void placeShips(List<Ship> ships, int width, int height) {
    for (Ship ship : ships) {
      int shipSize = ship.getShipType().getSize();
      boolean isValid = false;
      while (!isValid) {
        int rowCol = getRandomNumber(1, 3); // row or column arrangement
        int startX = 0;
        int startY = 0;
        if (rowCol == 1) { // row arrangement
          startX = getRandomNumber(0, (width - shipSize) - 1);
          startY = getRandomNumber(0, height - 1);
        } else { // column arrangement
          startX = getRandomNumber(0, width - 1);
          startY = getRandomNumber(0, (height - shipSize) - 1);
        }
        List<Coord> coords = getCoords(rowCol, startX, startY, shipSize);
        isValid = isCoordsValid(coords, ships);
        if (isValid) {
          ship.placeOnCoordinate(coords, rowCol);
        }
      }
    }
  }

  /**
   * Generates a random number between the specified minimum and maximum values (inclusive).
   *
   * @param min the minimum value
   * @param max the maximum value
   * @return a random number between the specified range
   */
  private int getRandomNumber(int min, int max) {
    Random r = new Random();
    if (max <= min) {
      max = 1;
    }
    return r.ints(1, min, max + 1).findFirst().getAsInt();
  }

  /**
   * Checks if the generated coordinates are valid
   * (i.e., they do not overlap with any existing ships).
   *
   * @param coords the generated coordinates
   * @param ships the list of existing ships
   * @return true if the coordinates are valid, false otherwise
   */
  private boolean isCoordsValid(List<Coord> coords, List<Ship> ships) {
    int count = 0;
    for (Ship ship : ships) {
      if (coords.stream().noneMatch(p -> ship.isCoordinateExist(p))) {
        count++;
      }
    }
    return (count == ships.size());
  }

  /**
   * Generates a list of coordinates based on the given arrangement,
   * starting position, and ship size.
   *
   * @param rowCol the arrangement (1 for row, 2 for column)
   * @param startX the starting X coordinate
   * @param startY the starting Y coordinate
   * @param shipSize the size of the ship
   * @return a list of coordinates representing the ship's position
   */
  private List<Coord> getCoords(int rowCol, int startX, int startY, int shipSize) {
    List<Coord> coords = new ArrayList<>();
    if (rowCol == 1) {
      for (int x = startX; x < startX + shipSize; x++) {
        coords.add(new Coord(x, startY));
      }
    } else {
      for (int y = startY; y < startY + shipSize; y++) {
        coords.add(new Coord(startX, y));
      }
    }
    return coords;
  }
}
