package view;

import cs3500.pa04.model.Coord;
import cs3500.pa04.view.UserView;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the UserView class.
 */
public class UserViewTest {
  @Test
  public void testUpdateUserViewBoard() {
    Map<Coord, String> userBoardCells = new HashMap<>();
    userBoardCells.put(new Coord(0, 1), "0");
    UserView userView = new UserView(8, 8);
    userView.updateUserBoardCells(userBoardCells);
  }

  @Test
  public void testUpdateAIViewBoard() {
    Map<Coord, String> aiBoardCells = new HashMap<>();
    aiBoardCells.put(new Coord(1, 0), "0");
    UserView userView = new UserView(8, 8);
    userView.updateUserBoardCells(aiBoardCells);
  }
}
