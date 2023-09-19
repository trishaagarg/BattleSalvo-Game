package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JSON message containing a volley of coordinates.
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<CoordJson> volley) {

  /**
   * Converts a list of Coord objects to a VolleyJson object.
   *
   * @param coords the list of Coord objects
   * @return the corresponding VolleyJson object
   */
  public static VolleyJson toVolley(List<Coord> coords) {
    VolleyJson volleyJson = new VolleyJson(new ArrayList<>());
    for (Coord coord : coords) {
      volleyJson.volley.add(new CoordJson(coord.x(), coord.y()));
    }
    return volleyJson;
  }

  /**
   * Converts the VolleyJson object to a list of Coord objects.
   *
   * @return the list of Coord objects
   */
  public List<Coord> toCoords() {
    List<Coord> coords = new ArrayList<>();
    for (CoordJson coordJson : volley) {
      coords.add(new Coord(coordJson.x(), coordJson.y()));
    }
    return coords;
  }
}
