package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON message for joining a game.
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {

  /**
   * Returns the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }
}
