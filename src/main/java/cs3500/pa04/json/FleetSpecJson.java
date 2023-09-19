package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the specifications of ship fleets in JSON format.
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carrier,
    @JsonProperty("BATTLESHIP") int battleship,
    @JsonProperty("DESTROYER") int destroyer,
    @JsonProperty("SUBMARINE") int submarine) {

  /**
   * Converts the FleetSpecJson object to a map of ShipType to fleet size.
   *
   * @return the map of ShipType to fleet size
   */
  public Map<ShipType, Integer> toShipFleets() {
    Map<ShipType, Integer> shipFleets = new HashMap<>();
    shipFleets.put(ShipType.Carrier, carrier);
    shipFleets.put(ShipType.Battleship, battleship);
    shipFleets.put(ShipType.Destroyer, destroyer);
    shipFleets.put(ShipType.Submarine, submarine);
    return shipFleets;
  }
}
