package players.ab;

import java.util.ArrayList;
import beans.Board;
import beans.Move;
import beans.Player;
import game.BoardManager;
import game.Game;
import players.rl.Feature;
import players.rl.FeatureRegistry;

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
    
    //TODO: Check for Tak too
    Feature[] features = FeatureRegistry.getFeaturesFor(Game.TIC_TAC_TOE);
    
    //will be 0 if it's player 0's turn, 1 if it's player 1's turn. Used in finding the index of the features for tic-tac-toe.
    int player = game.whoseTurn().compareTo(game.getPlayer(1));
    
    int sideWeight = 1;
    int cornerWeight = 2;
    int middleWeight = 3;
    
    //side squares have weight of sideWeight
    score += sideWeight * features[2+player].getValue() + sideWeight * features[6+player].getValue()
        + sideWeight * features[10+player].getValue() + sideWeight * features[14+player].getValue();
    
    //corner squares have weight of cornerWeight
    score += cornerWeight * features[0+player].getValue() + cornerWeight * features[4+player].getValue()
        + cornerWeight * features[12+player].getValue() + cornerWeight * features[16+player].getValue();
    
    //center square has weight of middleWeight
    score += middleWeight * features[8+player].getValue();
    
    return score;
  }
}
