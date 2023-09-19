package cs3500.pa04.model;

/**
 * Represents the types of ships available in the game.
 */
public enum ShipType {
  Carrier(6, " C "),
  Battleship(5, " B "),
  Destroyer(4, " D "),
  Submarine(3, " S ");

  private int size;
  private String id;

  /**
   * Creates a new ship type with the specified size and identifier.
   *
   * @param size the size of the ship
   * @param id the identifier or symbol used to represent the ship on the game board
   */
  ShipType(int size, String id) {
    this.size = size;
    this.id = id;
  }

  /**
   * Returns the size of the ship.
   *
   * @return the size of the ship
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Returns the identifier or symbol used to represent the ship on the game board.
   *
   * @return the identifier or symbol of the ship
   */
  public String getId() {
    return this.id;
  }
}
