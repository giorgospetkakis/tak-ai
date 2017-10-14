package game;

import java.util.ArrayList;
import java.util.Iterator;
import beans.AdditionMove;
import beans.Cell;
import beans.Move;
import beans.Player;
import beans.RemovalMove;
import beans.Stone;
import players.DummyPlayer;

public class TicTacToeGame extends Game {

  private static final int DEFAULT_BOARD_SIZE = 3;

  private static final String TYPE = "Tic-Tac-Toe";

  public TicTacToeGame() {
    super(DEFAULT_BOARD_SIZE, TYPE);
  }

  public TicTacToeGame(Player p1, Player p2) {
    super(DEFAULT_BOARD_SIZE, p1, p2, TYPE);
    BoardManager.initialize(this.getBoard());
    PlayerManager.initPlayerPieces(3, p1, p2);
  }

  @Override
  public int calculateScore() {
    if (this.getWinner() != null) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public ArrayList<Move> availableMoves() {
    ArrayList<Move> moveList = new ArrayList<Move>();
    Player current = this.whoseTurn();

    // using iterator to check every cell
    Iterator<Cell> iter = this.getBoard().getCells().values().iterator();

    while (iter.hasNext()) {
      Cell curCell = iter.next();

      // for tic-tac-toe it's simple, if the space is empty they can move there
      if (curCell.isEmpty()) {
        moveList.add(new AdditionMove(curCell, new Stone(current)));
      }
    }

    return moveList;
  }

  @Override
  public Player detectWinner() {
    if(this.getNumTurns() == 8) {
      return new DummyPlayer();
    }
    Player p1 = this.getPlayer(0);
    Player p2 = this.getPlayer(1);

    Player winner = null;

    // checking horizontal and vertical
    for (int i = 0; i < this.getBoard().getSize(); i++) {

      // horizontal

      if (!BoardManager.getCell(this.getBoard(), i, 0).isEmpty()
          && !BoardManager.getCell(this.getBoard(), i, 1).isEmpty()
          && !BoardManager.getCell(this.getBoard(), i, 2).isEmpty()) {
        if (BoardManager.getCell(this.getBoard(), i, 0).getOwner() == BoardManager
            .getCell(this.getBoard(), i, 1).getOwner()
            && BoardManager.getCell(this.getBoard(), i, 0).getOwner() == BoardManager
                .getCell(this.getBoard(), i, 2).getOwner()) {
          winner = BoardManager.getCell(this.getBoard(), i, 0).getOwner();
          break;
        }
      }

      // vertical
      if (!BoardManager.getCell(this.getBoard(), 0, i).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 1, i).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 2, i).isEmpty()) {
        if (BoardManager.getCell(this.getBoard(), 0, i).getOwner() == BoardManager
            .getCell(this.getBoard(), 1, i).getOwner()
            && BoardManager.getCell(this.getBoard(), 0, i).getOwner() == BoardManager
                .getCell(this.getBoard(), 2, i).getOwner()) {
          winner = BoardManager.getCell(this.getBoard(), 0, i).getOwner();
          break;
        }
      }
    }

    // if winner wasn't found in horizontal or vertical
    if (winner == null) {

      if (!BoardManager.getCell(this.getBoard(), 0, 0).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 1, 1).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 2, 2).isEmpty()) {
        if (BoardManager.getCell(this.getBoard(), 0, 0).getOwner() == BoardManager
            .getCell(this.getBoard(), 1, 1).getOwner()
            && BoardManager.getCell(this.getBoard(), 0, 0).getOwner() == BoardManager
                .getCell(this.getBoard(), 2, 2).getOwner()) {
          winner = BoardManager.getCell(this.getBoard(), 1, 1).getOwner();
        }
      }
      if (!BoardManager.getCell(this.getBoard(), 0, 2).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 1, 1).isEmpty()
          && !BoardManager.getCell(this.getBoard(), 2, 0).isEmpty()) {
        if (BoardManager.getCell(this.getBoard(), 0, 2).getOwner() == BoardManager
            .getCell(this.getBoard(), 1, 1).getOwner()
            && BoardManager.getCell(this.getBoard(), 0, 2).getOwner() == BoardManager
                .getCell(this.getBoard(), 2, 0).getOwner()) {
          winner = BoardManager.getCell(this.getBoard(), 1, 1).getOwner();
        }
      }
    }

    return winner;
  }

  @Override
  public void makeMove(Move move) {
    super.makeMove(move);
    if (move instanceof AdditionMove) {
      BoardManager.add(this.getBoard(), ((AdditionMove) move).getPiece(),
          ((AdditionMove) move).getCell());
    } else if (move instanceof RemovalMove) {
      BoardManager.remove(this.getBoard(), ((RemovalMove) move).getCell());
    }
  }
}
