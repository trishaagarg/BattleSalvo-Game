package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;
import java.util.Objects;

/**
 * Represents the status of a game in JSON format.
 */
public record GameStatusJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {

  /**
   * Converts the GameStatusJson object to a GameResult object.
   *
   * @return the GameResult object
   */
  public GameResult toGameResult() {
    GameResult gameResult = new GameResult();
    gameResult.setWon(Objects.equals(result, "WIN"));
    return gameResult;
  }
}
