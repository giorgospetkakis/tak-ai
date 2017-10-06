package beans.test;

import static org.junit.Assert.*;
import org.junit.Test;
import beans.Player;

public class PlayerTest {
  
  Player p1 = new Player(1);
  Player p2 = new Player(2);
  
  String default_p1_name = "White";
  
  @Test
  public void compareToTest() {
   assertEquals(0, p1.compareTo(p2)); 
   assertEquals(1, p1.compareTo(p1)); 
  }
  
  @Test
  public void getNameTest() {
    assertEquals(default_p1_name, p1.getName());
  }
  
  @Test
  public void getPlayerTypeTest() {
    assertEquals(1, p1.getPlayerType());
  }
  
  @Test
  public void setStonesAvailableTest() {
    p1.setStonesAvailable(1);
    assertEquals(1, p1.getStonesAvailable());
    
    p1.setCapstonesAvailable(1);
    assertEquals(1, p1.getCapstonesAvailable());
  }
  
  @Test
  public void getStoneTest() {
    p1.setStonesAvailable(0);
    assertNull(p1.getStone(false));
    
    p1.setStonesAvailable(2);
    assertNotNull(p1.getStone(true));
    assertEquals(1, p1.getStonesAvailable());
  }
  
  @Test
  public void getCapstoneTest() {
    p1.setCapstonesAvailable(0);
    assertNull(p1.getCapstone());
    
    p1.setCapstonesAvailable(2);
    assertNotNull(p1.getCapstone());
    assertEquals(1, p1.getCapstonesAvailable());
  }
}
