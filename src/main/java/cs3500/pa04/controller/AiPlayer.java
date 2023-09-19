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
 * AIPlayer is a class that implements the Player interface and
 * represents an AI-controlled player in a game.
 */
public class AiPlayer implements Player {

  private final List<Ship> ships;
  private final Board board;

  /**
   * Constructs an AIPlayer object with the given board.
   *
   * @param board the game board
   */
  public AiPlayer(Board board) {
    this.board = board;
    this.ships = new ArrayList<>();
  }

  /**
   * Returns the name of the player.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "user";
  }

  /**
   * Sets up the player's ships on the board according to the given specifications.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences
   *                       each ship should appear on the board
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
    board.updateAiView();
    return ships;
  }

  /**
   * Takes a shot on the opponent's board.
   *
   * @return the list of coordinates representing the shots
   */
  @Override
  public List<Coord> takeShots() {
    return board.takeShots();
  }

  /**
   * Reports the damage caused by the opponent's shots on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the list of coordinates representing the damaged ships
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coords = board.hit(opponentShotsOnBoard);
    board.updateAiView();
    return coords;
  }

  /**
   * Notifies the player about the successful hits on the opponent's ships.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  /**
   * Notifies the player about the end of the game.
   *
   * @param result the result of the game (win, loss, or draw)
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
