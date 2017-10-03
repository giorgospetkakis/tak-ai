package game.tictactoe;

import java.util.ArrayList;
import beans.Move;
import beans.Player;
import game.Game;

public class TicTacToeGame extends Game {
  
  private static final int DEFAULT_BOARD_SIZE = 3;
  
  private static final String TYPE = "TAK";
  
  public TicTacToeGame() {
    super(DEFAULT_BOARD_SIZE, TYPE);
  }

  public TicTacToeGame(Player p1, Player p2) {
    super(DEFAULT_BOARD_SIZE, p1, p2, TYPE);
    // TODO Auto-generated constructor stub
  }

  @Override
  public int calculateScore() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ArrayList<Move> availableMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Player detectWinner() {
    // TODO Auto-generated method stub
    return null;
  }

}
