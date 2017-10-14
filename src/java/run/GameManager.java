package run;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import beans.Move;
import beans.Player;
import game.BoardManager;
import game.Game;
import game.PlayerManager;
import game.TakGame;
import game.TicTacToeGame;
import io.GameFileManager;
import io.RecordsManager;

public class GameManager {

  public static final Logger logger = Logger.getLogger(GameManager.class);

  public static LinkedList<Game> games;

  private static Game current;

  static {
    games = new LinkedList<Game>();
  }

  /**
   * Starts a new game.
   * 
   * @param boardSize The size of the board
   * @param player1Type The type of player 1
   * @param player2Type The type of player 2
   */
  public static void newGame(String gameType, int boardSize, int player1Type, int player2Type) {
    Game newGame;
    Player p1 = PlayerManager.generatePlayer(gameType, player1Type);
    Player p2 = PlayerManager.generatePlayer(gameType, player2Type);

    // Make the game
    switch (gameType) {
      case "Tak": {
        newGame = new TakGame(boardSize, p1, p2);
        break;
      }
      case "Tic-Tac-Toe": {
        newGame = new TicTacToeGame(p1, p2);
        break;
      }
      default: {
        newGame = new TakGame(TakGame.DEFAULT_BOARD_SIZE, p1, p2);
      }
    }
    newGame.setHashCode(Date.from(Instant.now()).hashCode());
    logger.info(newGame.getType() + " Game " + newGame.hashCode() + " has been created.");
    games.add(newGame);
    logger.info(games.size());
  }

  /**
   * Loads a game state from a file.
   * 
   * @param filename The file to be read
   * @return An instance of the game in the file
   */
  public static Game loadGame(String filename) {
    try {
      return GameFileManager.loadGame(filename);
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Logs the start time of the application and initiates the game loop.
   */
  public static void startQueue() {
    logger.info("Game queue started");
    gameLoop();
  }

  /**
   * Starts a new game.
   * 
   * @param game The game to be started
   */
  private static void start(Game game) {
    game.setTimeElapsed(System.nanoTime());
    game.setGameState(Game.IN_PROGRESS);
    game.setCurrent(game.getPlayer((int) (Math.random() * 2)));
    logger.info(game.getType() + " Game " + game.hashCode() + " has started.");
  }

  /**
   * Ends a game, sets a winner and records the game.
   * 
   * @param game The game to be ended
   * @param winner The winner of the game
   */
  private static void end(Game game, Player winner, boolean record) {
    game.setGameState(Game.FINISHED);
    game.setTimeElapsed(System.nanoTime() - game.getTimeElapsed());
    game.setWinner(winner);
    game.setScore(game.calculateScore());
    
    logger.info(game.getType() + " Game " + game.hashCode() + " has ended.");
    if (record) {
      RecordsManager.record(game);
      GameFileManager.saveGame(game);
    }
    
    // Train based on score seen at the end of the game
    // Negative reward if the other player won
    for (int i = game.getMoves().size(); i > 0; i--) {
      System.out.println(game.getMoves().size());
      game.whoseTurn().train(game);
      game.swapCurrentPlayer();
      game.incrementTurn();
    }
  }

  /**
   * The main game loop for the game. Controls when game ends.
   * 
   * @param game The game of the loop
   */
  private static void gameLoop() {
    // End loop if there are no games left.
    if (games.isEmpty()) {
      return;
    }

    current = games.removeFirst();
    start(current);

    // Single game loop
    while (current.getGameState() == Game.IN_PROGRESS) {

      ArrayList<Move> movelist = current.availableMoves();
      Move chosenMove = current.whoseTurn().requestMove(current, movelist);
      current.makeMove(chosenMove);

      checkEndState(current);

      current.swapCurrentPlayer();
      current.incrementTurn();
    }
    // Run the next game
    gameLoop();
  }

  /**
   * Checks if a game has reached an end-state.
   * 
   * @param game The game to check the end-state for
   */
  private static void checkEndState(Game game) {
    Player winner = game.detectWinner();
    // End game if a winner is found
    if (winner != null) {
      end(game, winner, true);
    }
  }

  /**
   * Returns the current game being played.
   * 
   * @return the game being played.
   */
  public static Game getCurrent() {
    return current;
  }
}
