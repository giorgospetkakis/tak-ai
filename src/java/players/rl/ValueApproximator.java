package players.rl;

public abstract class ValueApproximator {

  protected double learningRate;

  protected double discount;

  public abstract void init();

  public abstract void update(int reward, double nextState, double currentState);

  public abstract double getValue();

  public abstract double[] getWeights();

}
