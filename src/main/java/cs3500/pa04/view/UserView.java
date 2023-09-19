package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays the user's view of the game boards.
 */
public class UserView {
  private Map<Coord, String> userBoardCells;
  private Map<Coord, String> aiBoardcells;
  private final int width;
  private final int height;

  /**
   * Constructs a `UserView` object with the specified width and height.
   *
   * @param width  the width of the game board
   * @param height the height of the game board
   */
  public UserView(int width, int height) {
    userBoardCells = new HashMap<>();
    aiBoardcells = new HashMap<>();
    this.width = width;
    this.height = height;
  }

  /**
   * Updates the user's board cells with the given values and displays the current user view.
   *
   * @param userBoardCells the map of coordinate-cell values for the user's board
   */
  public void updateUserBoardCells(Map<Coord, String> userBoardCells) {
    this.userBoardCells.clear();
    this.userBoardCells.putAll(userBoardCells);
    showCurrentUserView();
  }

  /**
   * Updates the AI's board cells with the given values.
   *
   * @param aiBoardcells the map of coordinate-cell values for the AI's board
   */
  public void updateAiBoardCells(Map<Coord, String> aiBoardcells) {
    this.aiBoardcells.clear();
    this.aiBoardcells.putAll(aiBoardcells);
  }

  /**
   * Displays the current user view of the boards.
   */
  public void showCurrentUserView() {
    StringBuilder userViewBuilder = new StringBuilder();
    System.out.println("\nOpponent Board Data:");
    StringBuilder headline = new StringBuilder();
    for (int s = 0; s < width; s++) {
      headline.append("   ").append(s);
    }
    System.out.println("  " + headline);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        String cell = aiBoardcells.get(new Coord(x, y));
        if (cell == null) cell = " 0 ";
        if (!(cell.equals("HIT") || cell.equals("MSD"))) {
          cell = " 0 ";
        }
        userViewBuilder.append(" ").append(cell);
      }

      System.out.println(getRowValue(y) + " " + userViewBuilder.toString());
      userViewBuilder.setLength(0);
    }
    System.out.println("\n");
    System.out.println("Your Board:");
    System.out.println("  " + headline);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        String cell = userBoardCells.get(new Coord(x, y));
        userViewBuilder.append(" ").append(cell);
      }
      System.out.println(getRowValue(y) + " " + userViewBuilder.toString());
      userViewBuilder.setLength(0);
    }
    System.out.println("\n ----------------------------------------------\n\n");

  }

  /**
   * Returns the formatted row value for display purposes.
   *
   * @param y the row index
   * @return the formatted row value
   */
  private String getRowValue(int y) {
    String row = String.valueOf(y);
    if (row.length() == 1) {
      row = " " + row;
    }
    return row;
  }
}
