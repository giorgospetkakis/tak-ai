package players.rl;

import beans.Board;
import beans.Capstone;
import beans.Cell;
import beans.Stone;
import game.BoardManager;
import game.Game;
import run.Application;
import run.GameManager;

import java.util.ArrayList;

public abstract class FeatureRegistry {

  private static ArrayList<Feature> TAK;

  private static ArrayList<Feature> TIC_TAC_TOE;

  static {

  }

  private static void init() {
     TAK = new ArrayList<Feature>();
    for (int p = 0; p < 2; p++) {
      final int player = p;
      // Checks for stacks between 2 and the size of the board
      TAK.add (
              new Feature(() -> {
                if(GameManager.getCurrent().getPlayers().indexOf(GameManager.getCurrent().whoseTurn()) == player){
                  Feature.setValue(1);
                } else {
                  Feature.setValue(0);
                }
              })
      );

      TAK.add (
              new Feature(() -> {
                if(GameManager.getCurrent().getPlayers().indexOf(GameManager.getCurrent().whoseTurn()) == player){
                  Feature.setValue((double)(GameManager.getCurrent().getPlayer(player).getFlatstonesInPlay() / GameManager.getCurrent().getBoard().getSize()));
                } else {
                  Feature.setValue(0);
                }
              })
      );

      for (byte i = 0; i < Application.boardSize; i++) {
          final byte y = i;
          // Checks for number of same-color pieces in a column
          TAK.add (
                  new Feature(() -> {
                      int sum = 0;
                      for(int l = 0; l < GameManager.getCurrent().getBoard().getSize(); l++) {
                          sum += (BoardManager.getCell(GameManager.getCurrent().getBoard(), y, l).getOwner())==GameManager.getCurrent().getPlayer(player) &&
                                  !BoardManager.getCell(GameManager.getCurrent().getBoard(), y, l).top().isStandingStone() ? 1 : 0;
                          if (sum >= GameManager.getCurrent().getBoard().getSize() - 1) {
                              sum += sum;
                          }
                      }
                      Feature.setValue(sum);
                  })
          );

          // Checks for the number of same-color pieces in a row
          TAK.add (
                  new Feature(() -> {
                      int sum = 0;
                      for(int l = 0; l < Application.boardSize; l++) {
                          sum += (BoardManager.getCell(GameManager.getCurrent().getBoard(), l, y).getOwner())==GameManager.getCurrent().getPlayer(player) &&
                                  !BoardManager.getCell(GameManager.getCurrent().getBoard(), l, y).top().isStandingStone() ? 1 : 0;
                      }
                      Feature.setValue(sum);
                  })
          );

        for (byte j = 0; j < Application.boardSize; j++) {
          final byte x = j;

          // Checks top cell value
          TAK.add (
                  new Feature(() -> {
                    final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                    if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                      if (currCell.top().isStandingStone()) {
                        Feature.setValue(1);
                      }
                    } else {
                      Feature.setValue(0);
                    }
                  })
          );


            // Checks top cell value
            TAK.add (
                    new Feature(() -> {
                        final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                        if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                            if (currCell.top() instanceof Capstone) {
                                Feature.setValue(3);
                            }
                        } else {
                            Feature.setValue(0);
                        }
                    })
            );


            // Checks top cell value
            TAK.add (
                    new Feature(() -> {
                        final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                        if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                            if (currCell.top() instanceof Stone && !currCell.top().isStandingStone()) {
                                Feature.setValue(2);
                            }
                        } else {
                            Feature.setValue(0);
                        }
                    })
            );

          // Checks for singleton pieces
          TAK.add (
                  new Feature(() -> {
                    final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                    if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                      if (currCell.getPieces().size() == 0) {
                        Feature.setValue(1);
                      }
                    } else {
                      Feature.setValue(0);
                    }
                  })
          );

          // Checks for stacks between 2 and the size of the board
          TAK.add (
                  new Feature(() -> {
                    final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                    if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                      if (currCell.getPieces().size() > 1 && currCell.getPieces().size() < GameManager.getCurrent().getBoard().getSize()) {
                        Feature.setValue(1);
                      }
                    } else {
                      Feature.setValue(0);
                    }
                  })
          );

          // Checks for stacks larger than the size of the board
          TAK.add (
                  new Feature(() -> {
                    final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                    if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                      if (currCell.getPieces().size() >  GameManager.getCurrent().getBoard().getSize()) {
                        Feature.setValue(currCell.getPieces().size() / GameManager.getCurrent().getBoard().getSize());
                      }
                    } else {
                      Feature.setValue(0);
                    }
                  })
          );

          // Checks stack size
          TAK.add (
                  new Feature(() -> {
                    final Cell currCell = BoardManager.getCell(GameManager.getCurrent().getBoard(), x, y);
                    if(!currCell.isEmpty() && currCell.getOwner() == GameManager.getCurrent().getPlayer(player)) {
                      Feature.setValue((currCell.getPieces().size() % 2) + 1);
                    } else {
                      Feature.setValue(0);
                    }
                  })
          );
        }
      }
    }
  }


  /**
   * Returns the feature-set for the given game.
   *
   * @param gameType The type of game, as found in the Game class
   */
  public static Feature[] getFeaturesFor(String gameType) {
    Feature[] ret;
    switch (gameType) {
      case Game.TAK: {
        if (TAK == null) {
          init();
        }
        ret = new Feature[TAK.size()];
        TAK.toArray(ret);
        return ret;
      }
      case Game.TIC_TAC_TOE: {
        ret = new Feature[TIC_TAC_TOE.size()];
        TIC_TAC_TOE.toArray(ret);
        return ret;
      }
      default: {
        ret = new Feature[TAK.size()];
        TAK.toArray(ret);
        return ret;
      }
    }
  }
}
