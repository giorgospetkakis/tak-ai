package game.tak.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import beans.Player;
import game.PlayerManager;

public class PlayerManagerTakTest {

  Player p1;

  @Before
  public void setup() {
    p1 = new Player();
  }

  @Test
  public void initPlayerPiecesTest() {
    assertNull(p1.getStone(true));
    for (int i = 3; i <= 9; i++) {
      PlayerManager.initPlayerPieces(i, p1);
      assertNotNull(p1.getStone(false));
      if (i >= 5) {
        assertNotNull(p1.getCapstone());

      } else {
        assertNull(p1.getCapstone());
      }
    }
  }
}
