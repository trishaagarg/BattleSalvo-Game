package play;

import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.play.ShipPlacement;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ShipPlacement class.
 */
public class ShipPlacementTest {
  @Test
  public void testShipPlacement() {
    ShipPlacement shipPlacement = new ShipPlacement();
    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship(ShipType.Carrier));
    ships.add(new Ship(ShipType.Destroyer));
    shipPlacement.placeShips(ships, 8, 8);
  }
}
