package cs3500.pa04.play;

import cs3500.pa04.model.Coord;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Manages shot coordinates in the game.
 */
public class ShotCoord {
  private Set<Coord> alreadyShot = new HashSet<>();
  private Set<Coord> unShot = new HashSet<>();
  private UserInput userInput;

  /**
   * Constructs a `ShotCoord` object.
   *
   * @param width the width of the game board
   * @param height the height of the game board
   * @param userInput the `UserInput` object for obtaining user input
   */
  public ShotCoord(int width, int height, UserInput userInput) {
    this.userInput = userInput;
    initialize(width, height);
  }

  /**
   * Initializes the shot coordinates with unshot positions.
   *
   * @param width the width of the game board
   * @param height the height of the game board
   */
  private void initialize(int width, int height) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        unShot.add(new Coord(x, y));
      }
    }
  }

  /**
   * Retrieves a list of random shot coordinates.
   *
   * @param count the number of shot coordinates to retrieve
   * @return a list of random shot coordinates
   */
  public List<Coord> getShotCoords(int count) {
    int unhitCount = unShot.size();
    Set<Coord> selected = new HashSet<>();
    List<Coord> unHitCoords = unShot.stream().toList();
    Random r = new Random();
    while (selected.size() < count) {
      if (unhitCount <= 0) {
        unhitCount = 1;
      }
      int randomIdx = r.ints(1, 0, unhitCount).findFirst().getAsInt();
      selected.add(unHitCoords.get(randomIdx));
    }
    alreadyShot.addAll(selected);
    unShot.removeAll(selected);
    return selected.stream().toList();
  }

  /**
   * Retrieves a list of shot coordinates from user input.
   *
   * @param count the number of shot coordinates to retrieve
   * @return a list of shot coordinates from user input
   */
  public List<Coord> getUserShotCoords(int count) {
    List<Coord> coords = userInput.getUserShotCoordinates(count);
    alreadyShot.addAll(coords);
    unShot.removeAll(coords);
    return coords;
  }
}
