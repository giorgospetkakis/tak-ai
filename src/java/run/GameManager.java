package run;

import java.io.IOException;
import org.apache.log4j.Logger;
import beans.Player;
import game.Game;
import game.TakGame;
import game.tictactoe.TicTacToeGame;
import io.GameFileManager;
import io.RecordsManager;

public class GameManager {

  public static final Logger logger = Logger.getLogger(GameManager.class);

  /**
   * Starts a new game.
   * 
   * @param boardSize
   * @param player1Type
   * @param player2Type
   */
  public static Game newGame(String gameType, int boardSize, int player1Type, int player2Type) {
    Game newGame;
    Player p1 = new Player(player1Type);
    Player p2 = new Player(player2Type);

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
    logger.info(newGame.getType() + " Game " + newGame.hashCode() + " has been created.");
    return newGame;
//    newGame.start();
//    logger.info(newGame.getType() + " Game " + newGame.hashCode() + " has started.");
//    gameLoop(newGame);
  }
  
  /**
   * Loads a game state from a file
   * @param filename The file to be read
   * @return An instance of the game in the file
   */
  public static Game loadGame(String filename) {
    try {
      return GameFileManager.readGameFromFile(filename);
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * The main game loop for the game. Controls when game ends.
   * 
   * @param game The game of the loop
   */
  public static void gameLoop(Game game) {

    while (game.getGameState() == Game.IN_PROGRESS) {

      // TODO: Add turn logic

      Player winner = game.detectWinner();
      // End-game sequence
      if (winner != null) {
        game.setWinner(winner);
        game.setScore(game.calculateScore());
        game.end();
        RecordsManager.record(game);
      }
    }
  }
}
