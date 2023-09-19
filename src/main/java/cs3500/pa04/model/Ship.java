package cs3500.pa04.model;

import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.Fleet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a ship in the game.
 */
public class Ship {
  private final ShipType shipType;
  private final List<Coord> location;
  private Direction direction;
  private final Map<Coord, Boolean> hitStatus;

  /**
   * Creates a new ship with the specified ship type.
   *
   * @param shipType the type of the ship
   */
  public Ship(ShipType shipType) {
    this.shipType = shipType;
    this.location = new ArrayList<>();
    this.hitStatus = new HashMap<>();
  }

  /**
   * Returns the ship type.
   *
   * @return the ship type
   */
  public ShipType getShipType() {
    return shipType;
  }

  /**
   * Returns true if the ship is sunk (all coordinates have been hit), false otherwise.
   *
   * @return true if the ship is sunk, false otherwise
   */
  public boolean getIsSunk() {
    return !hitStatus.containsValue(false);
  }

  /**
   * Converts the ship object to a fleet object for JSON serialization.
   *
   * @return the fleet representation of the ship
   */
  public Fleet toFleet() {
    CoordJson coordJson = new CoordJson(location.get(0).x(), location.get(0).y());
    return new Fleet(coordJson, location.size(), direction.getValue());
  }

  /**
   * Places the ship on the specified coordinates and sets its direction.
   *
   * @param coords  the coordinates occupied by the ship
   * @param rowCol  the orientation of the ship (1 for horizontal, 2 for vertical)
   */
  public void placeOnCoordinate(List<Coord> coords, int rowCol) {
    location.addAll(coords);
    if (rowCol == 1) {
      direction = Direction.Horizontal;
    } else {
      direction = Direction.Vertical;
    }
    for (Coord coord : coords) {
      hitStatus.put(coord, false);
    }
  }

  /**
   * Checks if the specified coordinate exists within the ship's location.
   *
   * @param coord  the coordinate to check
   * @return true if the coordinate exists in the ship's location, false otherwise
   */
  public boolean isCoordinateExist(Coord coord) {
    return location.stream().anyMatch(p -> p.equals(coord));
  }

  /**
   * Updates the game board cells to indicate the presence of the ship.
   *
   * @param cells  the game board cells
   */
  public void updateCoordinate(Map<Coord, String> cells) {
    for (Coord coord : location) {
      cells.put(coord, shipType.getId());
    }
  }

  /**
   * Marks the hit status of the ship's coordinates based on the provided hit coordinates.
   *
   * @param coords  the hit coordinates
   * @return a list of coordinates that were successfully hit
   */
  public List<Coord> hit(List<Coord> coords) {
    List<Coord> hitCoords = new ArrayList<>();
    for (Coord toHit : coords) {
      Optional<Coord> hit = location.stream().filter(p -> p.equals(toHit)).findFirst();
      if (hit.isPresent()) {
        hitStatus.put(hit.get(), true);
        hitCoords.add(hit.get());
      }
    }
    return hitCoords;
  }
}
