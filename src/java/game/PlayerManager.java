package game;

import beans.Player;
import players.AlphaBetaPlayer;
import players.DummyPlayer;
import players.HumanPlayer;
import players.ReinforcementLearningPlayer;

public abstract class PlayerManager {

  /**
   * Initiates the Player piece pool.
   * 
   * @param boardSize The size of the board (determines number of pieces)
   */
  public static void initPlayerPieces(int boardSize, Player... players) {
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
  
  /**
   * Instantiates the right kind of player for the type given.
   * @param type The type of player being instantiated
   * @return An instance of the desired player.
   */
  public static Player generatePlayer(int type) {
    switch (type) {
      case Player.DUMMY: {
        return new DummyPlayer();
      }
      case Player.HUMAN: {
        return new HumanPlayer();
      }
      case Player.ALPHA_BETA: {
        return new AlphaBetaPlayer();
      }
      case Player.REINF_LEARNING: {
        return new ReinforcementLearningPlayer();
      }
      case Player.NEURAL_NET: {
        return new ReinforcementLearningPlayer();
      }
      default: {
        return new DummyPlayer();
      }
    }
  }
}
