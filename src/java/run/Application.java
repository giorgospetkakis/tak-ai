package run;

import org.apache.log4j.Logger;
import beans.Game;
import game.BoardManager;
import game.GameManager;

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

  private static final Logger l = Logger.getLogger(Application.class);

  /**
   * Application main method.
   * 
   * @param args Console arguments
   */
  public static void main(String[] args) {
    Game g = new Game(3);

    GameManager.start(g);
    
    BoardManager.addPiece(g.getBoard(), g.getPlayers().get(0).getStone(false), g.getBoard().getCells().get(BoardManager.getByteCode(0, 0)));
    BoardManager.addPiece(g.getBoard(), g.getPlayers().get(0).getStone(false), g.getBoard().getCells().get(BoardManager.getByteCode(0, 1)));
    BoardManager.addPiece(g.getBoard(), g.getPlayers().get(0).getStone(false), g.getBoard().getCells().get(BoardManager.getByteCode(0, 2)));
    
    GameManager.gameLoop(g);
  }
}
