package players;

import java.util.ArrayList;
import beans.Move;
import beans.Player;
import game.Game;

public class DummyPlayer extends Player {

  @Override
  public Move requestMove(Game game, ArrayList<Move> availableMoves) {
    return availableMoves.get((int)(Math.random() * availableMoves.size()));
  }

  @Override
  public void train(Game game) {
  }
}
