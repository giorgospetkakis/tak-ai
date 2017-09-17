package run;

import org.apache.log4j.Logger;
import beans.Board;
import beans.Capstone;
import beans.Player;
import beans.Stone;

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
    l.info("Application Started");
    Board b5 = new Board(8);
    Player w = new Player();
    Player b = new Player();


    b5.addPiece(new Stone(w), "A1");
    l.info(b5.toString());
    b5.addPiece(new Capstone(b), "C4");
    l.info(b5.toString());
    // true as a second argument adds a standing stone
    b5.addPiece(new Stone(w, true), "D2");
    l.info(b5.toString());
    
    b5.moveTopPiece("A1", "A3");
    l.info(b5.toString());
  }
}
