package beans.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Stack;
import org.apache.log4j.Logger;
import org.junit.Test;
import beans.Cell;
import beans.Piece;
import beans.Player;
import beans.Stone;
import players.DummyPlayer;


public class CellTest {

  private static final Logger logger = Logger.getLogger(CellTest.class);
  byte dim = 5;

  Cell testCell;
  Cell northCell = new Cell(dim, (byte) (dim - 1));
  Cell southCell = new Cell(dim, (byte) (dim + 1));
  Cell eastCell = new Cell((byte) (dim + 1), dim);
  Cell westCell = new Cell((byte) (dim - 1), dim);

  Stack<Piece> pieceTest = new Stack<Piece>();
  Player p1 = new DummyPlayer();
  Stone testStone = new Stone(p1);

  @Test
  public void setYposTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setYpos(dim);
    assertEquals(dim, testCell.getYpos());
  }

  @Test
  public void setXposTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setXpos(dim);
    assertEquals(dim, testCell.getXpos());
  }

  @Test
  public void setNorthTest() {
    testCell = new Cell(dim, dim);
    testCell.setNorth(northCell);
    assertEquals(northCell, testCell.getNorth());
    assertEquals((byte) (dim - 1), testCell.getNorth().getYpos());
  }

  @Test
  public void setSouthTest() {
    testCell = new Cell(dim, dim);
    testCell.setSouth(southCell);
    assertEquals(southCell, testCell.getSouth());
    assertEquals((byte) (dim + 1), testCell.getSouth().getYpos());
  }

  @Test
  public void setWestTest() {
    testCell = new Cell(dim, dim);
    testCell.setWest(westCell);
    assertEquals(westCell, testCell.getWest());
    assertEquals((byte) (dim - 1), testCell.getWest().getXpos());
  }

  @Test
  public void setEastTest() {
    testCell = new Cell(dim, dim);
    testCell.setEast(eastCell);
    assertEquals(eastCell, testCell.getEast());
    assertEquals((byte) (dim + 1), testCell.getEast().getXpos());
  }

  @Test
  public void setPiecesTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setPieces(pieceTest);
    assertEquals(pieceTest, testCell.getPieces());
  }

  @Test
  public void isVisitedTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setVisited(true);
    assertTrue(testCell.isVisited());
  }

  @Test
  public void topTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setPieces(pieceTest);
    pieceTest.push(testStone);
    assertEquals(testStone, testCell.top());
  }

  @Test
  public void getOwnerTest() {
    testCell = new Cell((byte) 0, (byte) 0);
    testCell.setPieces(pieceTest);
    pieceTest.push(testStone);
    assertEquals(testStone.getOwner(), testCell.getOwner());
  }

  @Test
  public void toStringTest() {
    testCell = new Cell(dim, dim);

    testCell.setPieces(pieceTest);
    pieceTest.push(testStone);

    logger.debug(testCell.toString());

    testCell.setNorth(northCell);
    testCell.setSouth(southCell);
    testCell.setWest(westCell);
    testCell.setEast(eastCell);

    logger.debug(testCell.toString());
  }
}
