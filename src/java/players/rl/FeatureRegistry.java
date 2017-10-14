package players.rl;

import game.Game;
import run.GameManager;

public abstract class FeatureRegistry {

  private static Feature[] TAK;

  private static Feature[] TIC_TAC_TOE;

  static {
    TAK = new Feature[] {new Feature(() -> {
      if (GameManager.getCurrent().getPlayer(0).getCapstonesAvailable() > 0) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }),

        //
        new Feature(() -> {
          if (GameManager.getCurrent().getPlayer(1).getCapstonesAvailable() > 0) {
            Feature.setValue(1);
          } else {
            Feature.setValue(0);
          }
        }),

        new Feature(() -> {
          if (GameManager.getCurrent().getPlayer(1).getFlatstonesInPlay() > 20) {
            Feature.setValue(1);
          } else {
            Feature.setValue(0);
          }
        }),

        new Feature(() -> {
          if (GameManager.getCurrent().getPlayer(1).getFlatstonesInPlay() > 20) {
            Feature.setValue(1);
          } else {
            Feature.setValue(0);
          }
        })};
  }

  /**
   * Returns the feature-set for the given game.
   * 
   * @param gameType The type of game, as found in the Game class
   */
  public static Feature[] getFeaturesFor(String gameType) {
    switch (gameType) {
      case Game.TAK: {
        return TAK;
      }
      case Game.TIC_TAC_TOE: {
        return TIC_TAC_TOE;
      }
      default: {
        return TAK;
      }
    }
  }
}
