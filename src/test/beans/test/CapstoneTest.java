package beans.test;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import beans.Capstone;
import beans.Player;

public class CapstoneTest {

  @Test
  public void isStandingStoneTest() {
    Capstone c = new Capstone(new Player());
    assertFalse(c.isStandingStone());
  }
}
