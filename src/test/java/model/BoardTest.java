package model;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.play.UserInput;
import cs3500.pa04.view.UserView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Board class.
 */
public class BoardTest {
  @Test
  public void testTakeShots() {
    int width = 8;
    int height = 8;
    UserView userView = new UserView(width, height);
    UserInput userInput = new UserInput(new Scanner(System.in));
    Board board = new Board(width, height, userView, new GameResult(), userInput);
    List<Coord> coordList = board.takeShots();
    assertNotNull(coordList);
  }

  @Test
  public void testUnsunkShipCount() {
    int width = 8;
    int height = 8;
    UserView userView = new UserView(width, height);
    UserInput userInput = new UserInput(new Scanner(System.in));
    Board board = new Board(width, height, userView, new GameResult(), userInput);
    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.Carrier));
    ships.add(new Ship(ShipType.Destroyer));
    board.placeOnBoard(ships);
    int unSunkShipCount = board.getUnSunkShipCount();
    assertEquals(2, unSunkShipCount);
  }

  @Test
  public void testUpdateGameStatus() {
    int width = 8;
    int height = 8;
    UserView userView = new UserView(width, height);
    UserInput userInput = new UserInput(new Scanner(System.in));
    GameResult gameResult = new GameResult();
    Board board = new Board(width, height, userView, gameResult, userInput);
    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.Carrier));
    ships.add(new Ship(ShipType.Destroyer));
    board.placeOnBoard(ships);
    board.updateGameStatus("User");
    assertFalse(gameResult.isWon());
    assertEquals("User", gameResult.getUser());
  }
}
