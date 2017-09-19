package beans;

import java.util.HashMap;
import org.apache.log4j.Logger;
import game.BoardManager;

/**
 * A representation of a Tak board.
 * 
 * @author giorgospetkakis
 *
 */
public class Board {

  /**
   * Class Logger.
   */
  Logger logger = Logger.getLogger(Board.class);

  /**
   * The maximum allowed size of a board.
   */
  private static final int MAX_SIZE = 8;

  /**
   * The minimum allowed size of a board.
   */
  private static final int MIN_SIZE = 3;

  /**
   * The individual cells on the board.
   */
  private HashMap<Byte, Cell> cells;

  /**
   * The size of the board.
   */
  private int size;

  /**
   * A representation of a <code> size/size </size> Tak board.
   * 
   * @param size The size of the board
   */
  public Board(int size) {
    this.setSize(size);
    cells = new HashMap<Byte, Cell>();
    BoardManager.initializeBoard(this); //TODO: Move this in a different class
  }

  /**
   * Returns the size of the board.
   * 
   * @return the size
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the board size respecting preset max/min board sizes.
   * 
   * @param size The specified board size
   */
  private void setSize(int size) {
    this.size = Math.min(Math.max(size, MIN_SIZE), MAX_SIZE);
  }
  
  /**
   * Returns a HashMap of the board cells.
   * @return the individual board cells
   */
  public HashMap<Byte, Cell> getCells() {
    return cells;
  }
}
