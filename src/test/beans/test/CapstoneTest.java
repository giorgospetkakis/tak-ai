package beans.test;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import beans.Capstone;
import beans.Player;
import players.DummyPlayer;

public class CapstoneTest {

  @Test
  public void isStandingStoneTest() {
    Capstone c = new Capstone(new DummyPlayer());
    assertFalse(c.isStandingStone());
  }
}
