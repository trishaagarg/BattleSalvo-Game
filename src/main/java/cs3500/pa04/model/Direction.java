package cs3500.pa04.model;

/**
 * Represents the possible directions of a ship on the game board.
 */
public enum Direction {
  Vertical("VERTICAL"),
  Horizontal("HORIZONTAL");

  private final String value;

  Direction(String value) {
    this.value = value;
  }

  /**
   * Returns the value of the direction.
   *
   * @return the value of the direction
   */
  public String getValue() {
    return value;
  }
}
