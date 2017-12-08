package run;

import io.RecordsManager;
import org.apache.log4j.Logger;
import beans.Player;
import game.Game;

/**
 * TAK AI An AI player for the game Tak.
 * 
 * <p>
 * This project was created in 2017 by Adam Bolling and Giorgos Petkakis as part of CS322:
 * Artificial Intelligence at Skidmore College.
 * </p>
 * 
 * @author giorgospetkakis, keltfire88
 *
 */
public class Application {

  public static int boardSize;

  private static final Logger logger = Logger.getLogger(Application.class);

  /**
   * Application main method.
   * 
   * @param args Console arguments
   */
  public static void main(String[] args) {
    boardSize = Integer.parseInt(args[0]);
    
    for (int j = 0; j < 50; j++) {
      for (int i = 0; i < 1000; i++) {
        GameManager.newGame(Game.TAK, boardSize,Player.ALPHA_BETA, Player.QLEARNING_LINEAR);
        GameManager.startQueue();
      }
      logger.info("Played " + ((j+1) * 1000) + " games.");
    }

    GameManager.newGame(Game.TAK, boardSize, Player.HUMAN, Player.ALPHA_BETA);
    GameManager.startQueue();

    logger.info("Game queue empty. Application closing");
    Runtime.getRuntime().exit(0);
  }
}
