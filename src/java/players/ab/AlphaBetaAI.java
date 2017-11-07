package players.ab;

import java.util.ArrayList;
import beans.Board;
import beans.Move;
import beans.Player;
import game.BoardManager;
import game.Game;

public class AlphaBetaAI {
  
  public static Move nextMove(Player ai, Game game, int depth) {
    
    ArrayList<Move> moves = game.availableMoves();
    Move move = null;
    
    //accounting for if it starts as max or min at the same time using this boolean
    boolean isMax = ai.getName().equals("White");
    
    int threshold = Integer.MAX_VALUE;
    if(isMax)
      threshold = Integer.MIN_VALUE;
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      
      int val = AlphaBetaAI.minValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      if(isMax)
        val = AlphaBetaAI.maxValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      
      game.undoMove(moves.get(i));
      
      //if we're looking for max value, is it the new max, and if we're looking for min value, is it the new min. Either way we want to store this move
      if((isMax && val > threshold) || (!isMax && val < threshold))
        move = moves.get(i);
    }
    
    return move;
  }
  
  private static int maxValue(Game game, int depth, int alpha, int beta) {
    //if game is over
    if(game.getWinner() != null || depth <= 0)
      return AlphaBetaAI.calculateHeuristic(game);
    
    ArrayList<Move> moves = game.availableMoves();
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      int val = AlphaBetaAI.minValue(game, --depth, alpha, beta);
      game.undoMove(moves.get(i));
      
      //pruning
      if(val > beta) {
        alpha = val;
        break;
      }
      
      if(val > alpha)
        alpha = val;
    }
    
    return alpha;
  }
  
  private static int minValue(Game game, int depth, int alpha, int beta) {
    //if game is over
    if(game.getWinner() != null || depth <= 0)
      return AlphaBetaAI.calculateHeuristic(game);
    
    ArrayList<Move> moves = game.availableMoves();
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      int val = AlphaBetaAI.maxValue(game, --depth, alpha, beta);
      game.undoMove(moves.get(i));
      
      //pruning
      if(val < alpha) {
        beta = val;
        break;
      }
      
      if(val < beta)
        beta = val;
    }
    
    return beta;
  }
  
  private static int calculateHeuristic(Game game) {
    int score = 0;
    
    
    
    return score;
  }
}
