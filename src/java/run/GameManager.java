package run;

import org.apache.log4j.Logger;
import beans.Player;
import game.Game;
import game.tak.BoardManagerTak;
import game.tak.TakGame;
import game.tictactoe.TicTacToeGame;
import io.RecordsManager;

public class GameManager {

  public static final Logger logger = Logger.getLogger(GameManager.class);
  
  /**
   * Starts a new game.
   * @param boardSize
   * @param player1Type
   * @param player2Type
   */
  public static void newGame(String gameType, int boardSize, int player1Type, int player2Type) {
    Game newGame;
    
    // Make the game
    switch (gameType) {
      case "Tak": {
        newGame = new TakGame(boardSize, new Player(player1Type), new Player(player2Type));
        break;
      }
      case "TicTacToe": {
        newGame = new TicTacToeGame(new Player(player1Type), new Player(player2Type));
        break;
      }
      default: {
        newGame = new TakGame(TakGame.DEFAULT_BOARD_SIZE, new Player(), new Player());
      }
    }
    
    newGame.start();
    logger.info("Game " + newGame.hashCode() + " has started.");
    gameLoop(newGame);
  }

  /**
   * The main game loop for the game. Controls when game ends.
   * 
   * @param game The game of the loop
   */
  public static void gameLoop(Game game) {
    
    while (game.getGameState() == Game.IN_PROGRESS) {
      
      // TODO: Add turn logic
      
      BoardManagerTak.toString(game.getBoard());
      
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
