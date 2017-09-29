package game;

import beans.Board;
import beans.Capstone;
import beans.Cell;
import beans.Piece;
import beans.Player;
import beans.Stone;
import java.util.Stack;
import org.apache.log4j.Logger;

/**
 * Provides Board movement and piece management utilities.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class BoardManager {

  private static final Logger logger = Logger.getLogger(BoardManager.class);

  /**
   * Adds a new piece to the selected board position.
   *
   * @param board The board to add the piece to
   * @param piece The piece being added
   * @param cell The board position to add the piece
   */
  public static void addPiece(Board board, Piece piece, Cell cell) {
    if (isEmptyCell(board, cell)) {
      cell.getPieces().push(piece);
    }
  }

  /**
   * Moves a stack of pieces to the given cell.
   * 
   * @param src The current cell of the stack
   * @param dest The destination cell of the stack
   * @param size The size / height of the stack
   */
  public static void moveStack(Cell src, Cell dest, int size) {
    Stack<Piece> moveStack = new Stack<Piece>();
    // Pick up pieces
    for (int i = 0; i < size; i++) {
      moveStack.push(src.getPieces().pop());
    }
    // Drop pieces in order
    for (int i = 0; i < size; i++) {
      dest.getPieces().push(moveStack.pop());
    }
  }

  /**
   * Returns true if the cell is controlled by the specified Player.
   *
   * @param cell The cell to be examined
   * @param player The player in question
   * @return true if owned by the specified Player
   */
  public static boolean isControlledByPlayer(Cell cell, Player player) {
    return cell.getOwner().compareTo(player) == 1;
  }

  /**
   * Initializes the board HashMap, keys and neighbor relationships.
   * 
   * @param board The board to initialize
   */
  public static void initializeBoard(Board board) {
    // Initialize cell values and keys
    for (byte y = 0; y < board.getSize(); y++) {
      for (byte x = 0; x < board.getSize(); x++) {
        Cell currCell = new Cell(x, y);
        board.getCells().put(getCellKey(currCell), currCell);
      }
    }

    for (byte y = 0; y < board.getSize(); y++) {
      for (byte x = 0; x < board.getSize(); x++) {
        Cell currCell = board.getCells().get(getByteCode(x, y));

        // Set North
        if (currCell.getYpos() > 0) {
          currCell.setNorth(board.getCells().get(getByteCode(x, (y - 1))));
        }
        // Set South
        if (currCell.getYpos() < board.getSize() - 1) {
          currCell.setSouth(board.getCells().get(getByteCode(x, (y + 1))));
        }
        // Set East
        if (currCell.getXpos() > 0) {
          currCell.setWest(board.getCells().get(getByteCode(x - 1, y)));
        }
        // Set West
        if (currCell.getXpos() < board.getSize() - 1) {
          currCell.setEast(board.getCells().get(getByteCode(x + 1, y)));
        }
        // logger.debug("Initialized Cell" + currCell.toString());
      }
    }
  }

  /**
   * Returns a cell's unique cell key given its coordinates.
   * 
   * @param cell The given cell
   * @return The unique hash key
   */
  public static byte getCellKey(Cell cell) {
    return getByteCode(cell.getXpos(), cell.getYpos());
  }

  /**
   * Returns the byte-shift value for x and y coordinates.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the byte-shift value that corresponds to a hash key
   */
  public static byte getByteCode(int x, int y) {
    return (byte) (x | (y << 4));
  }
  
  /**
   * Returns the cell at the given coordinates.
   * 
   * @param board The board to retrieve the Cell from
   * @param x The x coordinate of the cell
   * @param y The y coordinate of the cell
   * @return The cell at the given coordinates
   */
  public static Cell getCell(Board board, int x, int y) {
    return board.getCells().get(getByteCode(x, y));
  }

  /**
   * Returns true if the specified cell is empty.
   * 
   * @param board the board on which the cell is
   * @param cell the selected cell position
   * @return true if cell is empty
   */
  public static boolean isEmptyCell(Board board, Cell cell) {
    return board.getCells().get(getCellKey(cell)).getPieces().isEmpty();
  }

  /**
   * Text method of displaying the current board.
   *
   * @param board The board to output
   * @return returns the current board state
   */
  public static String toString(Board board) {
    StringBuilder overview = new StringBuilder();
    StringBuilder stacks = new StringBuilder(); //TODO: Add a way to print contents of a stack

    overview.append("\n ");
    // Append letter cell reference
    for (int l = 0; l < board.getSize(); l++) {
      char app = (char) ('A' + l);
      overview.append(" " + app + "  ");
    }
    overview.append("\n");
    
    for (int y = 0; y < board.getSize(); y++) {
      // Append horizontal cell lines
      for (int k = 0; k < board.getSize(); k++) {
        overview.append("----");
      }
      
      // Append cell values and vertical lines
      overview.append("-\n|");
      for (int x = 0; x < board.getSize(); x++) {
        byte cellIdx = getByteCode(x, y);
        String rep = " ";
        if (!BoardManager.isEmptyCell(board, board.getCells().get(cellIdx))) {
          Piece cellContents = board.getCells().get(cellIdx).top();
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
        } else {
          rep += " ";
        }
        overview.append(rep + " |");
      }
      overview.append(" " + (y + 1) + "\n");
    }
    // Append horizontal cell lines
    for (int k = 0; k < board.getSize(); k++) {
      overview.append("----");
    }
    overview.append('-');
    return overview.toString() + stacks.toString();
  }
}
