package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.play.UserInput;
import cs3500.pa04.view.UserView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the AiPlayer class.
 */
public class AiPlayerTest {
  AiPlayer aiPlayer;

  @Test
  public void testPlayerSetup() {
    int width = 8;
    int height = 8;
    UserInput userInput = new UserInput(new Scanner(System.in));
    Board board = new Board(width, height, new UserView(width, height), new GameResult(),
        userInput);
    aiPlayer = new AiPlayer(board);
    List<Ship> ships = aiPlayer.setup(height, width, getShipSpec(1, 1, 1, 1));
    assertTrue(ships.size() > 0);
  }

  @Test
  public void testPlayerTakeShotsGivesCoordsEqualsToUnsunkShip() {
    int width = 8;
    int height = 8;
    UserInput userInput = new UserInput(new Scanner(System.in));
    Board board = new Board(width, height, new UserView(width, height),
        new GameResult(), userInput);
    aiPlayer = new AiPlayer(board);
    List<Ship> ships = aiPlayer.setup(height, width, getShipSpec(1, 1, 1, 1));
    List<Coord> coords = aiPlayer.takeShots();
    assertEquals(4, coords.size());
  }

  @Test
  public void testPlayerReportDamageReturnsCurrentHitCoords() {
    int width = 8;
    int height = 8;
    UserInput userInput = new UserInput(new Scanner(System.in));
    Board board = new Board(width, height, new UserView(width, height),
        new GameResult(), userInput);
    aiPlayer = new AiPlayer(board);
    aiPlayer.setup(height, width, getShipSpec(1, 1, 1, 1));
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 3));
    coords.add(new Coord(1, 2));
    coords.add(new Coord(2, 2));
    List<Coord> coordsHit = aiPlayer.reportDamage(coords);
    assertNotNull(coordsHit);
  }

  private Map<ShipType, Integer> getShipSpec(int c, int b, int d, int s) {
    Map<ShipType, Integer> spec = new HashMap<>();
    spec.put(ShipType.Carrier, c);
    spec.put(ShipType.Battleship, b);
    spec.put(ShipType.Destroyer, d);
    spec.put(ShipType.Submarine, s);
    return spec;
  }
}
