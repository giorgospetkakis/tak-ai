package game.tak.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import beans.Board;
import beans.Capstone;
import beans.Player;
import beans.Stone;
import game.BoardManager;

public class BoardManagerTakTest {

  private Board b1;
  private Stone s1, s2, s3, s4, s5;
  private Capstone c;
  private Player p1, p2;

  @Before
  public void setup() {
    p1 = new Player();
    p2 = new Player();
    
    s1 = new Stone(p1);
    s2 = new Stone(p1);
    s3 = new Stone(p1);
    s4 = new Stone(p1, true);
    c = new Capstone(p1);
    b1 = new Board(3);

    BoardManager.initialize(b1);
    
    BoardManager.addPiece(b1, s1, BoardManager.getCell(b1, 0, 0));
    BoardManager.addPiece(b1, s2, BoardManager.getCell(b1, 0, 0));
    BoardManager.addPiece(b1, s3, BoardManager.getCell(b1, 0, 1));
    BoardManager.addPiece(b1, s4, BoardManager.getCell(b1, 0, 1));
    BoardManager.addPiece(b1, s5, BoardManager.getCell(b1, 0, 0));
    BoardManager.addPiece(b1, c, BoardManager.getCell(b1, 1, 0));
  }

  @Test
  public void addPieceTest() {
    assertEquals(s2, BoardManager.getCell(b1, 0, 0).top());
  }

  @Test
  public void moveStackTest() {
    // Test moving multiple pieces
    BoardManager.moveStack(BoardManager.getCell(b1, 0, 1), BoardManager.getCell(b1, 0, 0), 2);
    assertEquals(s4, BoardManager.getCell(b1, 0, 0).top());
    assertEquals(4, BoardManager.getCell(b1, 0, 0).getPieces().size());

    // Test moving a single piece onto a stack, and crushing a standing stone
    assertTrue(s4.isStandingStone());
    BoardManager.moveStack(BoardManager.getCell(b1, 1, 0), BoardManager.getCell(b1, 0, 0), 1);
    assertEquals(c, BoardManager.getCell(b1, 0, 0).top());
    assertEquals(5, BoardManager.getCell(b1, 0, 0).getPieces().size());
    assertFalse(s4.isStandingStone());
  }
  
  @Test
  public void isOwnedByPlayerTest() {
    assertFalse(BoardManager.isControlledByPlayer(BoardManager.getCell(b1, 0, 0), p2));
    assertTrue(BoardManager.isControlledByPlayer(BoardManager.getCell(b1, 0, 0), p1));
  }
  
  @Test
  public void toStringTest() {
    assertNotNull(BoardManager.toString(b1));
  }
}
