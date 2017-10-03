package game;

import java.util.ArrayList;
import beans.Move;
import beans.Player;

public interface BoardGame {

  public abstract void start();
  
  public abstract void end();
  
  public abstract int calculateScore();
  
  public abstract ArrayList<Move> availableMoves();
  
  public abstract Player detectWinner();
}
