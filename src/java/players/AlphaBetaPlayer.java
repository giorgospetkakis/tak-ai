package players;

import java.util.ArrayList;
import beans.Move;
import beans.Player;
import game.Game;
import players.ab.AlphaBetaAI;

public class AlphaBetaPlayer extends Player {
  
  private static final int tttDepth = 5;
  private static final int takDepth = 50;
  
  @Override
  public Move requestMove(Game game, ArrayList<Move> availableMoves) {

    return AlphaBetaAI.nextMove(this, game, takDepth);
  }
}
