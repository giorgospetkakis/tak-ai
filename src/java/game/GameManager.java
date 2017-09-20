package game;

import beans.Cell;
import beans.Game;
import beans.Player;
import io.RecordsManager;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.log4j.Logger;

public abstract class GameManager {

  public static final Logger logger = Logger.getLogger(GameManager.class);

  /**
   * The main game loop for the game. Controls when game ends.
   * 
   * @param game The game of the loop
   */
  private static void gameLoop(Game game) {

    while (game.getGameState() == Game.IN_PROGRESS) {
      
      // TODO: Add turn logic
      
      BoardManager.toString(game.getBoard());
      
      
      Player winner = detectWinner(game);
      // End-game sequence
      if (winner != null) {
        game.setWinner(winner);
        game.setScore(calculateScore(game));
        end(game);
        RecordsManager.recordGame(game);
      }
    }
  }

  /**
   * Dijkstra-based algorithm that find the winning player. TODO: This works but may be a bit long
   * and wordy
   * 
   * @param game The game to be examined
   * @return The winning player. Null if there is no winner.
   */
  public static Player detectWinner(Game game) {
    // Run algorithm in horizontally then vertically
    for (int d = 0; d < 2; d++) {
      LinkedList<Cell> queue = new LinkedList<Cell>();
      HashMap<Cell, Cell> endpoints = new HashMap<Cell, Cell>();

      // Add start and end positions
      for (int i = 0; i < game.getBoard().getSize(); i++) {
        // This is to capture start (j = 0) and end (j = size-1) points in one block of code
        for (int j = 0; j < game.getBoard().getSize(); j += game.getBoard().getSize() - 1) {
          Cell currCell = d == 0 ? BoardManager.getCell(game.getBoard(), i, j)
              : BoardManager.getCell(game.getBoard(), j, i);
          if (!BoardManager.isEmptyCell(game.getBoard(), currCell)
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
            && !BoardManager.isEmptyCell(game.getBoard(), currCell.getNorth())
            && BoardManager.isControlledByPlayer(currCell, currCell.getNorth().getOwner())) {
          if (!currCell.getNorth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getNorth());
          }
        }

        if (currCell.getSouth() != null
            && !BoardManager.isEmptyCell(game.getBoard(), currCell.getSouth())
            && BoardManager.isControlledByPlayer(currCell, currCell.getSouth().getOwner())) {
          if (!currCell.getSouth().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getSouth());
          }
        }

        if (currCell.getWest() != null
            && !BoardManager.isEmptyCell(game.getBoard(), currCell.getWest())
            && BoardManager.isControlledByPlayer(currCell, currCell.getWest().getOwner())) {
          if (!currCell.getWest().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getWest());
          }
        }

        if (currCell.getEast() != null
            && !BoardManager.isEmptyCell(game.getBoard(), currCell.getEast())
            && BoardManager.isControlledByPlayer(currCell, currCell.getEast().getOwner())) {
          if (!currCell.getEast().isVisited() && !currCell.top().isStandingStone()) {
            queue.add(currCell.getEast());
          }
        }
      }
      // Reset nodes to unvisited
      for (Cell c : game.getBoard().getCells().values()) {
        c.setVisited(false);
      }
    }
    return null;
  }

  /**
   * Start the given game.
   * @param game The game to be started
   */
  public static void start(Game game) {
    game.setTimeElapsed(System.currentTimeMillis());
    game.setGameState(Game.IN_PROGRESS);
    logger.info("Game " + game.hashCode() + " has started.");
  }
  
  /**
   * End the given game.
   * @param game The game to be ended
   */
  public static void end(Game game) {
    game.setTimeElapsed(System.currentTimeMillis() - game.getTimeElapsed());
    game.setGameState(Game.FINISHED);
    logger.info("Game " + game.hashCode() + " has ended.");
  }

  /**
   * Calculates a player's score for a given game.
   * @param game The game of which the score will be calculated
   * @return The score in integer format
   */
  public static int calculateScore(Game game) {
    return game.getBoard().getSize() * game.getBoard().getSize()
        + game.getWinner().getCapstonesAvailable() + game.getWinner().getStonesAvailable();
  }
}
