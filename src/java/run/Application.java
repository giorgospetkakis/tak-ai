package run;

import org.apache.log4j.Logger;
import beans.Player;
import game.Game;
import io.GameFileManager;

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

    GameManager.newGame(Game.TAK, 6, Player.HUMAN, Player.HUMAN);
    
    GameManager.startQueue();

    logger.info("Game queue empty. Application closing");
    Runtime.getRuntime().exit(0);
  }
}
