package cs3500.pa04.model;

/**
 * Represents the result of a game.
 */
public class GameResult {
  private String user;
  private boolean isWon;

  /**
   * Returns the user associated with the game result.
   *
   * @return the user associated with the game result
   */
  public String getUser() {
    return user;
  }

  /**
   * Sets the user associated with the game result.
   *
   * @param user the user associated with the game result
   */
  public void setUser(String user) {
    this.user = user;
  }

  /**
   * Returns true if the game was won, false otherwise.
   *
   * @return true if the game was won, false otherwise
   */
  public boolean isWon() {
    return isWon;
  }

  /**
   * Sets whether the game was won or not.
   *
   * @param won true if the game was won, false otherwise
   */
  public void setWon(boolean won) {
    isWon = won;
  }
}
