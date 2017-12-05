package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import beans.AdditionMove;
import beans.Board;
import beans.Capstone;
import beans.Cell;
import beans.CompositeMove;
import beans.Move;
import beans.MovementMove;
import beans.Piece;
import beans.Player;
import beans.RemovalMove;
import beans.Stone;
import run.GameManager;

/**
 * This class holds the rules and logic of Tak.
 * 
 * @author giorgospetkakis
 *
 */
public class TakGame extends Game {

  public static final int DEFAULT_BOARD_SIZE = 5;

  /**
   * Creates a game of Tak with the specified parameters.
   * 
   * @param boardSize The size of the board
   */
  public TakGame(int boardSize) {
    super(boardSize, "Tak");
    BoardManager.initialize(this.getBoard());
  }

  /**
   * Creates a game of Tak with the specified parameters.
   * 
   * @param boardSize The size of the board
   * @param p1 Player 1
   * @param p2 Player 2
   */
  public TakGame(int boardSize, Player p1, Player p2) {
    super(boardSize, p1, p2, "Tak");
    BoardManager.initialize(this.getBoard());
    PlayerManager.initPlayerPieces(boardSize, p1, p2);
  }

  public static final Logger logger = Logger.getLogger(GameManager.class);

  /**
   * Breadth-First-Search algorithm that finds the winning player.
   * 
   * @return The winning player. Null if there is no winner.
   */
  @Override
  public Player detectWinner() {
    Player p1 = this.getPlayer(0);
    Player p2 = this.getPlayer(1);

    // Alternate win conditions

    // If either player runs out of pieces, the player with the most flat-stones in play wins
    if ((p1.getStonesAvailable() + p1.getCapstonesAvailable())
        * (p2.getStonesAvailable() + p2.getCapstonesAvailable()) == 0) {
      return p1.getFlatstonesInPlay() > p2.getFlatstonesInPlay() ? p1 : p2;
    }

    // If the board is covered, the player with the most flat-stones wins
    Iterator<Cell> cells = this.getBoard().getCells().values().iterator();
    Cell checkEmpty;
    while (cells.hasNext()) {
      checkEmpty = cells.next();
      if (checkEmpty.isEmpty()) {
        break;
      }
      if (!cells.hasNext()) {
        return p1.getFlatstonesInPlay() > p2.getFlatstonesInPlay() ? p1 : p2;
      }
    }

    // Main win condition
    // If there is a connected road from one side of the board of the same color,
    // to the other, then the player of the color wins

    // Run algorithm in horizontally then vertically
    for (int d = 0; d < 2; d++) {
      LinkedList<Cell> queue = new LinkedList<Cell>();
      HashMap<Cell, Cell> endpoints = new HashMap<Cell, Cell>();

      // Add start and end positions
      for (int i = 0; i < this.getBoard().getSize(); i++) {
        // This is to capture start (j = 0) and end (j = size-1) points in one block of code
        for (int j = 0; j < this.getBoard().getSize(); j += this.getBoard().getSize() - 1) {
          Cell currCell = d == 0 ? BoardManager.getCell(this.getBoard(), i, j)
              : BoardManager.getCell(this.getBoard(), j, i);
          if (!currCell.isEmpty() && !currCell.top().isStandingStone()) {
            // Add cell to start points
            if (j == 0) {
              queue.add(currCell);
              // Add cell to end points
            } else {
              endpoints.put(currCell, currCell);
            }
          }
        }
      } // End initialization

      if (endpoints.isEmpty()) {
        continue;
      }

      while (!queue.isEmpty()) {
        // Check existence of path for current direction
        Cell currCell = queue.remove();
        currCell.setVisited(true);

        // Return winning player if path is found
        if (endpoints.containsKey(currCell)) {
          return currCell.getOwner();
        }
        // Check all adjacent cells
        if (currCell.getNorth() != null && !currCell.getNorth().isEmpty()
            && BoardManager.isControlledByPlayer(currCell, currCell.getNorth().getOwner())) {
          if (!currCell.getNorth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getNorth());
          }
        }

        if (currCell.getSouth() != null && !currCell.getSouth().isEmpty()
            && BoardManager.isControlledByPlayer(currCell, currCell.getSouth().getOwner())) {
          if (!currCell.getSouth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getSouth());
          }
        }

        if (currCell.getWest() != null && !currCell.getWest().isEmpty()
            && BoardManager.isControlledByPlayer(currCell, currCell.getWest().getOwner())) {
          if (!currCell.getWest().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getWest());
          }
        }

        if (currCell.getEast() != null && !currCell.getEast().isEmpty()
            && BoardManager.isControlledByPlayer(currCell, currCell.getEast().getOwner())) {
          if (!currCell.getEast().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getEast());
          }
        }
      }
      // Reset nodes to unvisited
      for (Cell c : this.getBoard().getCells().values()) {
        c.setVisited(false);
      }
    }
    return null;
  }

  /**
   * Calculates a player's score for a given game.
   * 
   * @return The score in integer format
   */
  @Override
  public int calculateScore() {
    return this.getBoard().getSize() * this.getBoard().getSize()
        + this.getWinner().getCapstonesAvailable() + this.getWinner().getStonesAvailable();
  }
  
  @Override
  public ArrayList<Move> availableMoves() {
    ArrayList<Move> moveList = new ArrayList<Move>();
    Player current = this.whoseTurn();

    // Iterate over every cell to find available moves
    Iterator<Cell> iter = this.getBoard().getCells().values().iterator();

    while (iter.hasNext()) {
      Cell currCell = iter.next();
      // If cell is empty, we can add a piece there!
      if (currCell.isEmpty()) {
        if (current.getCapstonesAvailable() > 0) {
          moveList.add(new AdditionMove(currCell, new Capstone(current)));
        }
        if (current.getStonesAvailable() > 0) {
          moveList.add(new AdditionMove(currCell, new Stone(current, true)));
          moveList.add(new AdditionMove(currCell, new Stone(current, false)));
        }
        // If the current player has a piece in it, they can move the stack
      } else if (currCell.getOwner().compareTo(current) == 1) {
        // Handle any chosen stack-size
        for (int i = 1; i <= Math.min(this.getBoard().getSize(),
            currCell.getPieces().size()); i++) {
          // Handle all cardinal directions
          for (int d = 0; d < 4; d++) {
            // Handle awkward one-piece moves
            CompositeMove cm = new CompositeMove();
            makeComposite(cm, currCell, currCell, d, i);

            for (int j = cm.getMoves().size(); j > 0; j--) {
              moveList.add(new CompositeMove(cm.getMoves().subList(0, j)));
            }
          }
        }
      }
    }
    return moveList;
  }

  private static CompositeMove makeComposite(CompositeMove composite, Cell source, Cell target,
      int direction, int stackSize) {
    Cell newTarget = target.getDirection(direction);
    // If we hit the end of the board or have no more pieces return
    if (newTarget == null | stackSize <= 0) {
      return composite;
    }
    // Check if the move is valid, then add it and recurse
    if (validateMove(source.top(), newTarget, stackSize)) {
      composite.add(new MovementMove(target, newTarget, stackSize));
      return makeComposite(composite, source, newTarget, direction, stackSize - 1);
    } else {
      return composite;
    }
  }

  private static boolean validateMove(Piece top, Cell target, int stackSize) {
    // Don't move outside the board
    if (target == null) {
      return false;
    }

    // Don't move on top of Capstone
    if (!target.isEmpty() && target.top() instanceof Capstone) {
      return false;
    }

    // Don't move on top of standing stone
    if (!target.isEmpty() && target.top().isStandingStone()) {
      if (top instanceof Capstone && stackSize == 1) {
        return true;
      } else {
        return false;
      }
    }
    return true;
  }

  @Override
  public void makeMove(Move move) {
    super.makeMove(move);
    // Handle Addition moves
    if (move instanceof AdditionMove) {
      BoardManager.add(this.getBoard(), ((AdditionMove) move).getPiece(),
          ((AdditionMove) move).getCell());
      // Handle Movement moves
    } else if (move instanceof MovementMove) {
      BoardManager.move(((MovementMove) move).getSource(), ((MovementMove) move).getTarget(),
          ((MovementMove) move).getStackSize());
      // Handle Removal moves
    } else if (move instanceof RemovalMove) {
      BoardManager.remove(this.getBoard(), ((RemovalMove) move).getCell());
      // Recursively handle Composite moves
    } else if (move instanceof CompositeMove) {
      for (Move m : ((CompositeMove) move).getMoves()) {
        makeMove(m);
      }
    }
  }
}
