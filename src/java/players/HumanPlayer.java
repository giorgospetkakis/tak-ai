package players;

import java.util.ArrayList;
import java.util.Scanner;
import beans.Move;
import beans.Player;
import game.BoardManager;
import game.Game;

public class HumanPlayer extends Player {

  @Override
  public Move requestMove(Game game, ArrayList<Move> availableMoves) {
    System.out.println(game.whoseTurn().getName() + "\'s Turn(" + game.getNumTurns() + "):");
    System.out.println(BoardManager.toString(game.getBoard()));

    int moveCount = 0;
    for (Move m : availableMoves) {
      System.out.println(moveCount++ + " " + m.toString());
    }

    int choice = -1;
    Scanner sc = new Scanner(System.in);
    while (choice < 0 || choice >= availableMoves.size()) {
      choice = sc.nextInt();
    }
    return availableMoves.get(choice);
  }
}
