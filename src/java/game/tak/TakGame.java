package game.tak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import beans.Cell;
import beans.Move;
import beans.Player;
import game.Game;
import run.GameManager;

public class TakGame extends Game {
  
  public static final int DEFAULT_BOARD_SIZE = 5;
  
  public TakGame(int boardSize) {
    super(boardSize, "Tak");
  }
  
  public TakGame(int boardSize, Player p1, Player p2) {
    super(boardSize, p1, p2, "Tak");
  }

  public static final Logger logger = Logger.getLogger(GameManager.class);

  /**
   * Breadth-First-Search algorithm that finds the winning player.
   * 
   * @param game The game to be examined
   * @return The winning player. Null if there is no winner.
   */
  @Override
  public Player detectWinner() {
    // Run algorithm in horizontally then vertically
    for (int d = 0; d < 2; d++) {
      LinkedList<Cell> queue = new LinkedList<Cell>();
      HashMap<Cell, Cell> endpoints = new HashMap<Cell, Cell>();

      // Add start and end positions
      for (int i = 0; i < this.getBoard().getSize(); i++) {
        // This is to capture start (j = 0) and end (j = size-1) points in one block of code
        for (int j = 0; j < this.getBoard().getSize(); j += this.getBoard().getSize() - 1) {
          Cell currCell = d == 0 ? BoardManagerTak.getCell(this.getBoard(), i, j)
              : BoardManagerTak.getCell(this.getBoard(), j, i);
          if (!BoardManagerTak.isEmptyCell(this.getBoard(), currCell)
              && !currCell.top().isStandingStone()) {
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
        if (currCell.getNorth() != null
            && !BoardManagerTak.isEmptyCell(this.getBoard(), currCell.getNorth())
            && BoardManagerTak.isControlledByPlayer(currCell, currCell.getNorth().getOwner())) {
          if (!currCell.getNorth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getNorth());
          }
        }

        if (currCell.getSouth() != null
            && !BoardManagerTak.isEmptyCell(this.getBoard(), currCell.getSouth())
            && BoardManagerTak.isControlledByPlayer(currCell, currCell.getSouth().getOwner())) {
          if (!currCell.getSouth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getSouth());
          }
        }

        if (currCell.getWest() != null
            && !BoardManagerTak.isEmptyCell(this.getBoard(), currCell.getWest())
            && BoardManagerTak.isControlledByPlayer(currCell, currCell.getWest().getOwner())) {
          if (!currCell.getWest().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getWest());
          }
        }

        if (currCell.getEast() != null
            && !BoardManagerTak.isEmptyCell(this.getBoard(), currCell.getEast())
            && BoardManagerTak.isControlledByPlayer(currCell, currCell.getEast().getOwner())) {
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
   * @param game The game of which the score will be calculated
   * @return The score in integer format
   */
  @Override
  public int calculateScore() {
    return this.getBoard().getSize() * this.getBoard().getSize()
        + this.getWinner().getCapstonesAvailable() + this.getWinner().getStonesAvailable();
  }

  @Override
  public ArrayList<Move> availableMoves() {
    // TODO Auto-generated method stub
    return null;
  }
}
