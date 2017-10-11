package game;

import java.util.ArrayList;
import beans.Move;
import beans.Player;

public interface BoardGame {
  
  public abstract int calculateScore();
  
  public abstract ArrayList<Move> availableMoves();
  
  public abstract void makeMove(Move move);
  
  public abstract void undoMove(Move move);
  
  public abstract Player detectWinner();
}
