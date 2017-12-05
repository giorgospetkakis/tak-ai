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
    int picked = 0;
    
    //accounting for if it starts as max or min at the same time using this boolean
    boolean isMax = ai.equals(game.getPlayer(0));
    
    int threshold = Integer.MAX_VALUE;
    if(isMax)
      threshold = Integer.MIN_VALUE;
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      
      int val = 0;
      
      if(isMax)
        val = AlphaBetaAI.maxValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      else
        val = AlphaBetaAI.minValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      
      game.undoMove(moves.get(i));
      
      System.out.println(val);
      
      //if we're looking for max value, is it the new max, and if we're looking for min value, is it the new min. Either way we want to store this move
      if((isMax && val > threshold) || (!isMax && val < threshold))
      {
        move = moves.get(i);
        picked = val;
      }
    }
    
    System.out.println("picked " + picked);
    
    return move;
  }
  
  private static int maxValue(Game game, int depth, int alpha, int beta) {
    //if game is over
    if(game.getWinner() != null || depth <= 0)
      return AlphaBetaAI.calculateHeuristic(game, true);
    
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
      return AlphaBetaAI.calculateHeuristic(game, false);
    
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
  
  private static int calculateHeuristic(Game game, boolean max) {
    int score = 0;
    
    int winWeight = 1000;
    
    if(game.getType().equals(Game.TIC_TAC_TOE)) {
      Feature[] features = FeatureRegistry.getFeaturesFor(Game.TIC_TAC_TOE);
      
      //will be 0 if it's player 0's turn, 1 if it's player 1's turn. Used in finding the index of the features for tic-tac-toe.
      int player = game.whoseTurn().compareTo(game.getPlayer(1));
      
      double topLeft = features[0+player].getValue();
      double topMid = features[2+player].getValue();
      double topRight = features[4+player].getValue();
      double left = features[6+player].getValue();
      double mid = features[8+player].getValue();
      double right = features[10+player].getValue();
      double botLeft = features[12+player].getValue();
      double botMid = features[14+player].getValue();
      double botRight = features[16+player].getValue();
      
      int enemy = Math.abs(player - 1);
      
      double etopLeft = features[0+enemy].getValue();
      double etopMid = features[2+enemy].getValue();
      double etopRight = features[4+enemy].getValue();
      double eleft = features[6+enemy].getValue();
      double emid = features[8+enemy].getValue();
      double eright = features[10+enemy].getValue();
      double ebotLeft = features[12+enemy].getValue();
      double ebotMid = features[14+enemy].getValue();
      double ebotRight = features[16+enemy].getValue();
      
      int sideWeight = 1;
      int cornerWeight = 2;
      int middleWeight = 3;
      
      int noLoseWeight = 50;
      
      if(topLeft + topMid + topRight == 3 || left + mid + right == 3 || botLeft + botMid + botRight == 3
          || topLeft + left + botLeft == 3 || topMid + mid + botMid == 3 || topRight + right + botRight == 3
          || topLeft + mid + botRight == 3 || topRight + mid + botLeft == 3)
      //if(game.getWinner() != null)
        score = winWeight;
      else if(etopLeft + topMid + topRight == 3 || topLeft + etopMid + topRight == 3 || topLeft + topMid + etopRight == 3
          || eleft + mid + right == 3 || left + emid + right == 3 || left + mid + eright == 3
          || ebotLeft + botMid + botRight == 3 || botLeft + ebotMid + botRight == 3 || botLeft + botMid + ebotRight == 3
          || etopLeft + left + botLeft == 3 || topLeft + eleft + botLeft == 3 || topLeft + left + ebotLeft == 3
          || etopMid + mid + botMid == 3 || topMid + emid + botMid == 3 || topMid + mid + ebotMid == 3
          || etopRight + right + botRight == 3 || topRight + eright + botRight == 3 || topRight + right + ebotRight == 3
          || etopLeft + mid + botRight == 3 || topLeft + emid + botRight == 3 || topLeft + mid + ebotRight == 3
          || etopRight + mid + botLeft == 3 || topRight + emid + botLeft == 3 || topRight + mid + ebotLeft == 3)
        score = noLoseWeight;
      else {
        //side squares have weight of sideWeight
        score += sideWeight * topMid + sideWeight * left
            + sideWeight * right + sideWeight * botMid;
      
        //corner squares have weight of cornerWeight
        score += cornerWeight * topLeft + cornerWeight * topRight
            + cornerWeight * botLeft + cornerWeight * botRight;
      
        //center square has weight of middleWeight
        score += middleWeight * mid;
      }
      
      if(!max) score = -score;
    }
    else if(game.getType().equals(Game.TAK)) {
      Player winner = game.detectWinner();
      
      if(winner != null) {
        if(winner.equals(game.getPlayer(0))) {
          score = winWeight;
          game.setWinner(game.getPlayer(0));
        }
        else {
          score = -winWeight;
          game.setWinner(game.getPlayer(1));
        }
        
        score *= game.calculateScore();
      }
      else {    //there is no winner yet
        score = game.getCellsControlled(game.getPlayer(0)) - game.getCellsControlled(game.getPlayer(1));
      }
      
    }
    
    System.out.println("Score: " + score);
    
    return score;
  }
}
