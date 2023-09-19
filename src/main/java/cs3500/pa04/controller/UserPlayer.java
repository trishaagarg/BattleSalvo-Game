package cs3500.pa04.controller;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a user player in the game.
 */
public class UserPlayer implements Player {

  private final List<Ship> ships;
  private final Board board;

  /**
   * Constructs a UserPlayer with the specified board.
   *
   * @param board the board for the user player
   */
  public UserPlayer(Board board) {
    this.board = board;
    this.ships = new ArrayList<>();
  }

  /**
   * Returns the name of the user player.
   *
   * @return the name of the user player
   */
  @Override
  public String name() {
    return "user";
  }

  /**
   * Sets up the user player's ships on the board.
   *
   * @param height         the height of the board
   * @param width          the width of the board
   * @param specifications the ship specifications
   * @return the list of ships set up on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        ships.add(new Ship(entry.getKey()));
      }
    }
    board.placeOnBoard(ships);
    board.updateUserView();
    return ships;
  }

  /**
   * Takes shots by the user player.
   *
   * @return the list of coordinates representing the shots
   */
  @Override
  public List<Coord> takeShots() {
    return board.takeUserShots();
  }

  /**
   * Reports the damage caused by the opponent player's shots on the user player's board.
   *
   * @param opponentShotsOnBoard the coordinates of the opponent's shots on the board
   * @return the list of coordinates representing the damage caused by the opponent's shots
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coords = board.hit(opponentShotsOnBoard);
    board.updateUserView();
    return coords;
  }

  /**
   * Notifies the user player about the successful hits made by the opponent player.
   *
   * @param shotsThatHitOpponentShips the coordinates of the shots that hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    // No action needed for the user player
  }

  /**
   * Notifies the user player about the end of the game.
   *
   * @param result the game result
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    // No action needed for the user player
  }
}
