package players.ab;

import java.util.ArrayList;
import beans.Board;
import beans.Cell;
import beans.Move;
import beans.Player;
import game.BoardManager;
import game.Game;
import players.rl.Feature;
import players.rl.FeatureRegistry;
<<<<<<< HEAD
=======
import run.GameManager;
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed

public class AlphaBetaAI {
  
  private static int STAND_WEIGHT = 1;
  private static int FLAT_WEIGHT = 5;
  private static int WIN_WEIGHT = 10;
  
  public static Move nextMove(Player ai, Game game, int depth) {
    
    ArrayList<Move> moves = game.availableMoves();
    Move move = null;
    int picked = 0;
    
    //accounting for if it starts as max or min at the same time using this boolean
    boolean isMax = ai.equals(game.getPlayer(0));
    
    int threshold = Integer.MAX_VALUE;
    if(isMax)
      threshold = Integer.MIN_VALUE;
    
    //used for randomly picking between ties
    ArrayList<Integer> vals = new ArrayList<Integer>();
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      
      int val = 0;
      
      if(isMax)
        val = AlphaBetaAI.minValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      else
        val = AlphaBetaAI.maxValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      else
        val = AlphaBetaAI.minValue(game, --depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
      
      game.undoMove(moves.get(i));
      
      //if we're looking for max value, is it the new max, and if we're looking for min value, is it the new min. Either way we want to store this move
      if((isMax && val > threshold) || (!isMax && val < threshold))
      {
        move = moves.get(i);
<<<<<<< HEAD
        picked = val;
      }
    }
    
    System.out.println("picked " + picked);
=======
        threshold = val;
        
        for(int j = vals.size()-1; j >= 0; j--)
          vals.remove(j);
        vals.add(i);
      }
      else if(val == threshold)
        vals.add(i);
    }
    
    move = moves.get(vals.get((int) (Math.random() * vals.size())));
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
    
    return move;
  }
  
  private static int maxValue(Game game, int depth, int alpha, int beta) {
    //if game is over
    if(game.getWinner() != null || depth <= 0)
      return AlphaBetaAI.calculateHeuristic(game, true);
    
    ArrayList<Move> moves = game.availableMoves();
    
    ArrayList<Integer> maxes = new ArrayList<Integer>();
    
    maxes.add(alpha);
    
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
      {
        alpha = val;
        for(int j = maxes.size()-1; j >= 0; j--)
          maxes.remove(j);
        maxes.add(alpha);
        
      }
      
      if(val == alpha)
        maxes.add(val);
    }
    
    alpha = maxes.get((int) (Math.random() * maxes.size()));
    
    return alpha;
  }
  
  private static int minValue(Game game, int depth, int alpha, int beta) {
    //if game is over
    if(game.getWinner() != null || depth <= 0)
      return AlphaBetaAI.calculateHeuristic(game, false);
    
    ArrayList<Move> moves = game.availableMoves();
    
    ArrayList<Integer> mins = new ArrayList<Integer>();
    
    mins.add(beta);
    
    for(int i = 0; i < moves.size(); i++) {
      game.makeMove(moves.get(i));
      int val = AlphaBetaAI.maxValue(game, --depth, alpha, beta);
      game.undoMove(moves.get(i));
      
      //pruning
      if(val < alpha) {
        beta = val;
        break;
      }
      
      if(val < beta) {
        beta = val;
        for(int j = mins.size()-1; j >= 0; j--)
          mins.remove(j);
        mins.add(beta);
      }
      
      if(val == beta)
        mins.add(val);
    }
    
    beta = mins.get((int) (Math.random() * mins.size()));
    
    return beta;
  }
  
<<<<<<< HEAD
  private static int calculateHeuristic(Game game, boolean max) {
    int score = 0;
    
    int winWeight = 1000;
    
=======
  public static int calculateHeuristic(Game game, boolean max) {
    int score = 0;
    
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
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
<<<<<<< HEAD
        score = winWeight;
=======
        score = AlphaBetaAI.WIN_WEIGHT;
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
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
<<<<<<< HEAD
          score = winWeight;
          game.setWinner(game.getPlayer(0));
        }
        else {
          score = -winWeight;
=======
          score = AlphaBetaAI.WIN_WEIGHT;
          game.setWinner(game.getPlayer(0));
        }
        else {
          score = -AlphaBetaAI.WIN_WEIGHT;
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
          game.setWinner(game.getPlayer(1));
        }
        
        score *= game.calculateScore();
<<<<<<< HEAD
      }
      else {    //there is no winner yet
        score = game.getCellsControlled(game.getPlayer(0)) - game.getCellsControlled(game.getPlayer(1));
      }
      
    }
    
    System.out.println("Score: " + score);
=======
        
        game.setWinner(null);
      }
      else {    //there is no winner yet
        int cellsp0 = game.getCellsControlled(game.getPlayer(0));
        int standCellsp0 = game.getCellsControlledStanding(game.getPlayer(0));
        int cellsp1 = game.getCellsControlled(game.getPlayer(1));
        int standCellsp1 = game.getCellsControlledStanding(game.getPlayer(1));
        
        //flat stones and standing stones of player 1 on the top of stacks minus flat stones and standing stones of
        //player 2 on the top of stacks. Both are weighted by FLAT_WEIGHT and STAND_WEIGHT respectively.
        score = ((cellsp0 - standCellsp0) * AlphaBetaAI.FLAT_WEIGHT + standCellsp0 * AlphaBetaAI.STAND_WEIGHT)
            - ((cellsp1 - standCellsp1) * AlphaBetaAI.FLAT_WEIGHT + standCellsp1 * AlphaBetaAI.STAND_WEIGHT);
      }
      
    }
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
    
    return score;
  }
}
