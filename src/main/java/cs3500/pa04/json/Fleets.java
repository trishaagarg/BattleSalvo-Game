package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of fleets in JSON format.
 */
public record Fleets(
    @JsonProperty("fleet") List<Fleet> fleetList) {

  /**
   * Converts a list of ships to Fleets.
   *
   * @param ships the list of ships
   * @return the Fleets object
   */
  public static Fleets toFleets(List<Ship> ships) {
    Fleets fleets = new Fleets(new ArrayList<>());
    for (Ship ship : ships) {
      fleets.fleetList.add(ship.toFleet());
    }
    return fleets;
  }
}
