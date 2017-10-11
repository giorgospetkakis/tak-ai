package players;

import java.util.ArrayList;
import beans.Move;
import game.Game;

public interface Playable {
  
  public abstract Move requestMove(Game game, ArrayList<Move> availableMoves);
}
