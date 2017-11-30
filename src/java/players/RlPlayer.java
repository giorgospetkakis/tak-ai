package players;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import beans.Move;
import beans.Player;
import game.Game;
import players.rl.FeatureRegistry;
import players.rl.LinearApproximator;
import players.rl.NeuralNetApproximator;
import players.rl.ValueApproximator;

public class RlPlayer extends Player {

  public static final int SARSA = 0;

  public static final int Q_LEARNING = 1;

  public static final int LINEAR_APPROXIMATION = 100;

  public static final int NEURAL_NETWORK_APPROXIMATION = 101;

  public static final double epsilon = 0.05;
  
  private static final Logger logger = Logger.getLogger(RlPlayer.class);

  private ValueApproximator approx;

  private int algo;

  /**
   * The reward at the next state when backtracking.
   */
  private double nextStateValue;

  /**
   * The reward at the current state.
   */
  private int reward;

  /**
   * Explicit Reinforcement Learning Player Algorithm.
   * 
   * @param algorithm The algorithm to use. 0 for Sarsa, 1 for QLearning
   * @param approximationMethod 0 for Linear, 1 for Neural Net
   */
  public RlPlayer(String gameType, int algorithm, int approximationMethod) {
    super();

    this.algo = algorithm;

    switch (approximationMethod) {
      case LINEAR_APPROXIMATION: {
         approx = new LinearApproximator(FeatureRegistry.getFeaturesFor(gameType));
        break;
      }

      case NEURAL_NETWORK_APPROXIMATION: {
        approx = new NeuralNetApproximator();
        break;
      }
      default: {
        approx = new LinearApproximator(FeatureRegistry.getFeaturesFor(gameType));
        break;
      }
    }
  }

  @Override
  public Move requestMove(Game game, ArrayList<Move> availableMoves) {
    // Evaluate value of every move based on current weights
    double[] valueTable = new double[availableMoves.size()];
    for (int i = 0; i < availableMoves.size(); i++) {
      Move m = availableMoves.get(i);
      game.makeMove(m);
      valueTable[i] = approx.getValue();
      game.undoMove(m);
    }

    int chosenIndex = chooseMove(valueTable);
    game.makeMove(availableMoves.get(chosenIndex));
    if (game.detectWinner() != null) {
      game.setWinner(game.whoseTurn());
      this.reward = game.calculateScore();
      game.setWinner(null);
    } else {
      this.reward = 0;
    }
    game.undoMove(availableMoves.get(chosenIndex));

    if(this.algo == SARSA) {
      this.train(game, valueTable[chosenIndex]);
    } else if (algo == Q_LEARNING) {
      this.train(game, valueTable[getMax(valueTable)]);
    }
    return availableMoves.get(chosenIndex);
  }

  public void train(Game game, double trainStateValue) {
    double currentStateValue = approx.getValue();

    approx.update(reward, trainStateValue, currentStateValue);
  }

  private int getMax(double[] valueTable) {
    int index = 0;
    for (int i = 0; i < valueTable.length; i++) {
      if (valueTable[i] >= valueTable[index]) {
        index = i;
      }
    }
    return index;
  }

  private int chooseMove(double[] valueTable) {
    if(Math.random() <= epsilon) {
      return (int)(Math.random() * valueTable.length);
    } else {
      return getMax(valueTable);
    }
  }

  public void setNextStateValue(double nextStateValue) {
    this.nextStateValue = nextStateValue;
  }

  public double getNextStateValue() {
    return nextStateValue;
  }
}
