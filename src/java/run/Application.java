package run;

import java.io.IOException;
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
    String filename = "resources/tak/sample-boards/test.tak";

    try {
      Game g = GameFileManager.readGameFromFile(filename);
      logger.info(g.whoseTurn());
      
      GameFileManager.writeGameToFile(g);
    } catch (IOException e) {
      logger.error("Could not read file " + filename);
    }
  }
}
