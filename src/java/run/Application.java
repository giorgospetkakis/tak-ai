package run;

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

  private static final Logger logger = Logger.getLogger(Application.class);

  /**
   * Application main method.
   * 
   * @param args Console arguments
   */
  public static void main(String[] args) {
    logger.info("Hello");
//    for (int i = 0; i < 5; i++) {
      GameManager.newGame(Game.TIC_TAC_TOE, 3, Player.ALPHA_BETA, Player.HUMAN);
      GameManager.startQueue();
//    }

    logger.info("Game queue empty. Application closing");
    Runtime.getRuntime().exit(0);
  }
}
