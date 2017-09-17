package beans;

import java.util.HashMap;
import java.util.Stack;
import org.apache.log4j.Logger;

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
  private HashMap<String, Stack<Piece>> cells;

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
    cells = new HashMap<String, Stack<Piece>>();
    this.init();
  }

  /**
   * Adds a new piece to the selected board position.
   * 
   * @param piece The piece being added
   * @param cell The board position to add the piece
   */
  public void addPiece(Piece piece, String cell) {
    if (isEmptyCell(cell)) {
      cells.put(cell, new Stack<Piece>());
      cells.get(cell).push(piece);
    }
  }

  /**
   * Moves a single piece from one cell to another.
   * 
   * @param source The cell the piece is currently in
   * @param destination The destination cell
   */
  @SuppressWarnings(value = {"unused"})
  private void moveTopPiece(String source, String destination) {
    cells.get(destination).push(cells.get(source).pop());
  }

  /**
   * Returns true if the specified cell is empty.
   * 
   * @param cell the selected cell position
   * @return true if cell is empty
   */
  public boolean isEmptyCell(String cell) {
    return cells.containsKey(cell) ? cells.get(cell).isEmpty() : false;
  }

  /**
   * Returns true if the cell is controlled by the specified Player.
   * 
   * @param cell the cell to be examined
   * @param player the player in question
   * @return true if owned by the specified Player
   */
  public boolean isOwnedByPlayer(String cell, Player player) {
    return cells.get(cell).peek().getOwner().compareTo(player) == 1;
  }

  /**
   * Convert coordinates from 0-index to HashMap keys.
   * 
   * @param xpos The x-position on the array coordinate system
   * @param ypos The y-position on the array coordinate system
   * @return The corresponding HashMap index key
   */
  public String getCellIndex(int xpos, int ypos) {
    char x = (char) (xpos + 'A');
    ypos += 1;
    return "" + x + "" + ypos;
  }

  /**
   * Text method of displaying the current board.
   * @return returns the current board state
   */
  public String toString() {
    StringBuilder overview = new StringBuilder();
    StringBuilder stacks = new StringBuilder();
    
    overview.append("\n");
    for (int i = 0; i < size; i++) {
      // Append horizontal cell lines
      for (int k = 0; k < size; k++) {
        overview.append("---");
      }
      // Append cell values
      overview.append("-\n|");
      for (int j = 0; j < size; j++) {
        String cellIdx = getCellIndex(j, i);
        String rep = " ";
        if (!isEmptyCell(cellIdx)) {
          Piece cellContents = cells.get(cellIdx).peek();
          rep = cellContents.getOwner().getName().substring(0, 1);
          if (cellContents instanceof Capstone) {
            rep = " " + rep.toUpperCase();
          } else if (cellContents instanceof Stone) {
            rep = rep.toLowerCase();
            if (((Stone) cellContents).isStandingStone()) {
              rep = "/" + rep;
            } else {
              rep = " " + rep;
            }
          }
        }
        overview.append(rep + " |");
      }
      overview.append("\n");
    }
    // Append horizontal cell lines
    for (int k = 0; k < size; k++) {
      overview.append("---");
    }
    overview.append('-');
    return overview.toString();
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
   * Initializes the board HashMap and keys.
   */
  private void init() {
    for (char i = 'A'; i <= 'A' + size; i++) {
      for (int j = 1; j <= size; j++) {
        cells.put("" + i + j, new Stack<Piece>());
      }
    }
  }
}
