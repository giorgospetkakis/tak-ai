package beans.test;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import beans.Player;
import beans.Stone;

public class StoneTest {
  
  @Test
  public void isStandingStoneTest() {
    Stone testStone = new Stone(new Player(), true);
    assertTrue(testStone.isStandingStone());
  }
}
