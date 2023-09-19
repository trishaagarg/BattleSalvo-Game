package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Ship class.
 */
public class ShipTest {

  @Test
  public void testPlaceShipOnCoordinate() {
    Ship ship = new Ship(ShipType.Carrier);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 3));
    coords.add(new Coord(1, 2));
    coords.add(new Coord(2, 2));
    ship.placeOnCoordinate(coords, 1);
    assertTrue(ship.isCoordinateExist(new Coord(2, 2)));
  }

  @Test
  public void testGetShipSunkStatus() {
    Ship ship = new Ship(ShipType.Carrier);
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 3));
    coords.add(new Coord(1, 2));
    coords.add(new Coord(2, 2));
    ship.placeOnCoordinate(coords, 1);
    boolean isSunk = ship.getIsSunk();
    assertFalse(isSunk);
  }
}
