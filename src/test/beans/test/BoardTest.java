package beans.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import beans.Board;
import game.BoardManager;

public class BoardTest {
  
  private Board board;
  
  @Test
  public void getSizeTest() {
    board = new Board(3);
    assertEquals(board.getSize(), 3);
  }
  
  @Test
  public void getCellsTest() {
    board = new Board(3);
    BoardManager.initialize(board);
    org.junit.Assert.assertNotNull(board.getCells());
  }
}
