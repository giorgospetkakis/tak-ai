package game.tak;

import beans.Player;

public class PlayerManagerTak {

  /**
   * Initiates the Player piece pool.
   * 
   * @param p The p to set the pieces for
   * @param boardSize The size of the board (determines number of pieces)
   */
  public static void initPlayerPieces(int boardSize, Player ... players) {
    for (Player p : players) {
      switch (boardSize) {
        case 3: {
          p.setStonesAvailable(10);
          p.setCapstonesAvailable(0);
          break;
        }
        case 4: {
          p.setStonesAvailable(15);
          p.setCapstonesAvailable(0);
          break;
        }
        case 5: {
          p.setStonesAvailable(21);
          p.setCapstonesAvailable(1);
          break;
        }
        case 6: {
          p.setStonesAvailable(30);
          p.setCapstonesAvailable(1);
          break;
        }
        case 7: {
          p.setStonesAvailable(40);
          p.setCapstonesAvailable(2);
          break;
        }
        case 8: {
          p.setStonesAvailable(50);
          p.setCapstonesAvailable(2);
          break;
        }
        default: {
          p.setStonesAvailable(21);
          p.setCapstonesAvailable(1);
          break;
        }
      }
    }
  }
}
