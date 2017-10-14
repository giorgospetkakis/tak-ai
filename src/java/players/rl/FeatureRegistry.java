package players.rl;

import game.BoardManager;
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

    TIC_TAC_TOE = new Feature[] {new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 0, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 1, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 0).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 0).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 1).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 1).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(0)) == 1) {
        Feature.setValue(1);
      } else {
        Feature.setValue(0);
      }
    }), new Feature(() -> {
      if (!BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 2).isEmpty()
          && BoardManager.getCell(GameManager.getCurrent().getBoard(), 2, 2).getOwner()
              .compareTo(GameManager.getCurrent().getPlayer(1)) == 1) {
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
