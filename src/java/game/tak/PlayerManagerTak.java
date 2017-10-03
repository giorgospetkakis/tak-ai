package game.tak;

import beans.Player;

public class PlayerManagerTak {
  
  /**
   * Initiates the Player piece pool.
   * @param player The player to set the pieces for
   * @param boardSize The size of the board (determines number of pieces)
   */
  public static void initPlayerPieces(Player player, int boardSize) {
    switch (boardSize) {
      case 3: {
        player.setStonesAvailable(10);
        player.setCapstonesAvailable(0);
        break;
      }
      case 4: {
        player.setStonesAvailable(15);
        player.setCapstonesAvailable(0);
        break;
      }
      case 5: {
        player.setStonesAvailable(21);
        player.setCapstonesAvailable(1);
        break;
      }
      case 6: {
        player.setStonesAvailable(30);
        player.setCapstonesAvailable(1);
        break;
      }
      case 7: {
        player.setStonesAvailable(40);
        player.setCapstonesAvailable(2);
        break;
      }
      case 8: {
        player.setStonesAvailable(50);
        player.setCapstonesAvailable(2);
        break;
      }
      default: {
        player.setStonesAvailable(21);
        player.setCapstonesAvailable(1);
        break; 
      }
    }
  }
}
