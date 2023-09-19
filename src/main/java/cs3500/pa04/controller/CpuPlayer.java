package cs3500.pa04.controller;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.UserView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CpuPlayer is a class that implements the Player interface and represents
 * a CPU-controlled player in a game.
 */
public class CpuPlayer implements Player {

  private final List<Ship> cpuShips;
  private final List<Ship> serverShips;
  private Board cpuBoard;
  private Board serverBoard;
  private final GameResult gameResult;
  private final String name = "trishaagarg";

  /**
   * Constructs a CpuPlayer object.
   */
  public CpuPlayer() {
    gameResult = new GameResult();
    this.cpuShips = new ArrayList<>();
    this.serverShips = new ArrayList<>();
  }

  /**
   * Returns the name of the player.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Sets up the player's ships on the board according to the given specifications.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship
   *                       should appear on the board
   * @return the list of ships set up on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    UserView userView = new UserView(width, height);
    cpuBoard = new Board(width, height, userView, gameResult, null);
    serverBoard = new Board(width, height, userView, gameResult, null);

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        cpuShips.add(new Ship(entry.getKey()));
      }
    }

    System.out.println("setup...");
    cpuBoard.placeOnBoard(cpuShips);
    initServerBoard(specifications);
    cpuBoard.updateUserView();
    return cpuShips;
  }

  /**
   * Initializes the server's board with ships based on the given specifications.
   *
   * @param specifications a map of ship type to the number of occurrences
   *                       each ship should appear on the board
   */
  private void initServerBoard(Map<ShipType, Integer> specifications) {
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        serverShips.add(new Ship(entry.getKey()));
      }
    }
    serverBoard.placeOnBoard(serverShips);
    serverBoard.updateServerView();
  }

  /**
   * Takes shots on the opponent's board.
   *
   * @return the list of coordinates representing the shots
   */
  @Override
  public List<Coord> takeShots() {
    return serverBoard.takeServerShots(getUnSunkShipCount());
  }

  /**
   * Returns the count of unsunk ships owned by the CPU player.
   *
   * @return the count of unsunk ships
   */
  public int getUnSunkShipCount() {
    return (int) cpuShips.stream().filter(p -> !p.getIsSunk()).count();
  }

  /**
   * Notifies the player about the successful hits on the opponent's ships.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    serverBoard.updateSuccessfulHits(shotsThatHitOpponentShips);
    System.out.println("Take shot & Successful Hits...");
    serverBoard.updateServerView();
  }

  /**
   * Handles the notification of successful hits made by the opponent.
   * Updates the server board, prints a message, and updates the server view.
   *
   * @param opponentShotsOnBoard the list of coordinates where successful hits were made
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coords = cpuBoard.hit(opponentShotsOnBoard);
    System.out.println("Opponent hit & Report Damage...");
    cpuBoard.updateUserView();
    return coords;
  }

  /**
   * Handles the end of the game.
   * Prints the game result (WIN or LOSE) and the reason for the end of the game.
   *
   * @param result the game result
   * @param reason the reason for the end of the game
   */
  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(result.isWon() ? "WIN" : "LOSE");
    System.out.println(reason);
  }
}
