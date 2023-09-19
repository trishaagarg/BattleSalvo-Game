package cs3500.pa04.model;

import cs3500.pa04.play.ShipPlacement;
import cs3500.pa04.play.ShotCoord;
import cs3500.pa04.play.UserInput;
import cs3500.pa04.view.UserView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a game board for the battleship game.
 */
public class Board {
  private final int width;
  private final int height;
  private final List<Ship> ships;
  private final Map<Coord, String> cells;
  private final ShipPlacement shipPlacement;
  private final ShotCoord shotCoord;
  private final UserView userView;
  private final GameResult gameResult;
  private final List<Coord> currentShots;

  /**
   * Constructs a Board object with the specified width, height, userView, gameResult, and userInput
   *
   * @param width      the width of the board
   * @param height     the height of the board
   * @param userView   the user view
   * @param gameResult the game result
   * @param userInput  the user input
   */
  public Board(int width, int height, UserView userView, GameResult gameResult,
               UserInput userInput) {
    this.width = width;
    this.height = height;
    cells = new HashMap<>();
    initialize();
    ships = new ArrayList<>();
    this.userView = userView;
    this.gameResult = gameResult;
    shipPlacement = new ShipPlacement();
    currentShots = new ArrayList<>();
    shotCoord = new ShotCoord(width, height, userInput);
  }

  private void initialize() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        cells.put(new Coord(x, y), " 0 ");
      }
    }
  }

  /**
   * Updates the user view with the current state of the board.
   */
  public void updateUserView() {
    userView.updateUserBoardCells(cells);
    updateGameStatus("AI");
  }

  /**
   * Updates the AI view with the current state of the board.
   */
  public void updateAiView() {
    userView.updateAiBoardCells(cells);
    updateGameStatus("User");
  }

  /**
   * Updates the server view with the current state of the board.
   */
  public void updateServerView() {
    userView.updateAiBoardCells(cells);
    updateGameStatus("Server");
  }

  /**
   * Takes shots on the board.
   *
   * @return the list of coordinates representing the shots
   */
  public List<Coord> takeShots() {
    int unSunkShipCount = getUnSunkShipCount();
    List<Coord> currentShots = shotCoord.getShotCoords(unSunkShipCount);
    this.currentShots.addAll(currentShots);
    return currentShots;
  }

  /**
   * Takes shots from the server on the board.
   *
   * @param unSunkShipCount the count of unsunk ships
   * @return the list of coordinates representing the shots
   */
  public List<Coord> takeServerShots(int unSunkShipCount) {
    List<Coord> currentShots = shotCoord.getShotCoords(unSunkShipCount);
    this.currentShots.addAll(currentShots);
    return currentShots;
  }

  /**
   * Takes shots from the user on the board.
   *
   * @return the list of coordinates representing the shots
   */
  public List<Coord> takeUserShots() {
    int unSunkShipCount = getUnSunkShipCount();
    return shotCoord.getUserShotCoords(unSunkShipCount);
  }

  /**
   * Gets the count of unsunk ships on the board.
   *
   * @return the count of unsunk ships
   */
  public int getUnSunkShipCount() {
    return (int) ships.stream().filter(p -> !p.getIsSunk()).count();
  }

  /**
   * Updates the game status based on the given user.
   *
   * @param user the user
   */
  public void updateGameStatus(String user) {
    boolean isWon = ships.stream().allMatch(p -> p.getIsSunk());
    gameResult.setUser(user);
    gameResult.setWon(isWon);
  }

  /**
   * Places the given ships on the board.
   *
   * @param ships the list of ships to place on the board
   */
  public void placeOnBoard(List<Ship> ships) {
    shipPlacement.placeShips(ships, width, height);
    for (Ship ship : ships) {
      ship.updateCoordinate(cells);
    }
    this.ships.addAll(ships);
  }

  /**
   * Updates the cells on the board with successful hits.
   *
   * @param hitCoords the list of coordinates representing successful hits
   */
  public void updateSuccessfulHits(List<Coord> hitCoords) {
    for (Coord msd : this.currentShots) {
      cells.put(msd, "MSD");
    }
    for (Coord hit : hitCoords) {
      cells.put(hit, "HIT");
    }
    currentShots.clear();
  }

  /**
   * Hits the ships on the board at the given coordinates.
   *
   * @param coords the list of coordinates to hit
   * @return the list of coordinates representing successful hits
   */
  public List<Coord> hit(List<Coord> coords) {
    List<Coord> successfulHit = new ArrayList<>();
    for (Ship ship : ships) {
      List<Coord> hitCoords = ship.hit(coords);
      if (hitCoords.size() > 0) {
        successfulHit.addAll(hitCoords);
        for (Coord hit : hitCoords) {
          cells.put(hit, "HIT");
        }
      }
    }
    List<Coord> missedCoords = coords.stream().filter(p -> !successfulHit.contains(p)).toList();
    for (Coord msd : missedCoords) {
      cells.put(msd, "MSD");
    }
    return successfulHit;
  }
}
