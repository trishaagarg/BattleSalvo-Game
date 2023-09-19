package cs3500.pa04.controller;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.play.UserInput;
import cs3500.pa04.view.UserView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game is a class that represents the BattleSalvo game.
 */
public class Game {

  private Board userBoard;
  private Board aiBoard;
  private Player userPlayer;
  private Player aiPlayer;
  private UserView userView;
  private UserInput userInput;
  private GameResult gameResult;

  /**
   * Constructs a Game object with the given game result and user input.
   *
   * @param gameResult the game result object
   * @param userInput  the user input object
   */
  public Game(GameResult gameResult, UserInput userInput) {
    this.gameResult = gameResult;
    this.userInput = userInput;
  }

  /**
   * Begins the game with the specified number of loops.
   *
   * @param loopCount the number of loops to play the game
   */
  public void beginGame(int loopCount) {
    System.out.println("Hello! Welcome to the OOD BattleSalvo Game!");
    int[] boardDimension = userInput.getBoardDimension();
    int[] shipFleetSize = userInput.getShipFleetSize();
    userView = new UserView(boardDimension[0], boardDimension[1]);
    userBoard = new Board(boardDimension[0], boardDimension[1], userView, gameResult, userInput);
    aiBoard = new Board(boardDimension[0], boardDimension[1], userView, gameResult, userInput);
    userPlayer = new UserPlayer(userBoard);
    aiPlayer = new AiPlayer(aiBoard);
    userPlayer.setup(boardDimension[0], boardDimension[1], getShipFleets(shipFleetSize));
    aiPlayer.setup(boardDimension[0], boardDimension[1], getShipFleets(shipFleetSize));
    play(userPlayer, aiPlayer, loopCount);
  }

  /**
   * Plays the game with the given players for the specified number of loops.
   *
   * @param userPlayer the user player
   * @param aiPlayer   the AI player
   * @param loopCount  the number of loops to play the game
   */
  private void play(Player userPlayer, Player aiPlayer, int loopCount) {
    userView.showCurrentUserView();
    for (int i = 0; i < loopCount; i++) {
      List<Coord> userShots = userPlayer.takeShots();
      aiPlayer.reportDamage(userShots);
      userView.showCurrentUserView();
      if (gameResult.isWon()) {
        System.out.println("\n\n" + gameResult.getUser() + " Won.!");
        break;
      }
      List<Coord> aiShots = aiPlayer.takeShots();
      userPlayer.reportDamage(aiShots);
      System.out.println("\nAI taken a shot");
      userView.showCurrentUserView();
      if (gameResult.isWon()) {
        System.out.println("\n\n" + gameResult.getUser() + " Won.!");
        break;
      }
    }
  }

  /**
   * Returns a map of ship types to their fleet sizes based on the given ship fleet sizes.
   *
   * @param shipFleetSize the ship fleet sizes
   * @return the map of ship types to fleet sizes
   */
  private Map<ShipType, Integer> getShipFleets(int[] shipFleetSize) {
    Map<ShipType, Integer> shipFleets = new HashMap<>();
    shipFleets.put(ShipType.Carrier, shipFleetSize[0]);
    shipFleets.put(ShipType.Battleship, shipFleetSize[1]);
    shipFleets.put(ShipType.Destroyer, shipFleetSize[2]);
    shipFleets.put(ShipType.Submarine, shipFleetSize[3]);
    return shipFleets;
  }
}

