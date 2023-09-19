package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a fleet in JSON format.
 */
public record Fleet(
    @JsonProperty("coord") CoordJson coordJson,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}
