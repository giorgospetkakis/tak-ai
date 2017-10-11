package beans.test;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import beans.Stone;
import players.DummyPlayer;

public class StoneTest {
  
  @Test
  public void isStandingStoneTest() {
    Stone testStone = new Stone(new DummyPlayer(), true);
    assertTrue(testStone.isStandingStone());
  }
}
