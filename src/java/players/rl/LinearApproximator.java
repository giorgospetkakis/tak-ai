package players.rl;

import org.apache.log4j.Logger;
import io.WeightCacheManager;

/**
 * 
 * @author giorgospetkakis
 *
 */
public class LinearApproximator extends ValueApproximator {

  private Feature[] features;

  private static double[] weights;

  private static boolean initialized = false;
  
  private static final Logger logger = Logger.getLogger(LinearApproximator.class);

  /**
   * Creates a new Linear Approximator for the given feature set.
   * 
   * @param features The features of the approximation function
   */
  public LinearApproximator(Feature[] features) {
    this.setFeatures(features);
    this.learningRate = 0.0001;
    this.discount = .005;

    if(!initialized) {
      this.init();
    }
  }

  @Override
  public void init() {
    setWeights(new double[features.length]);
    for(int w = 0; w < weights.length; w++) {
      weights[w] = Math.random();
    }
    initialized = true;
  }

  @Override
  public void update(int reward, double nextStateValue, double currentStateValue) {
    for (int i = 0; i < weights.length; i++) {
      setWeight(i, getWeight(i) + (learningRate * (reward + (discount * nextStateValue) - currentStateValue) * features[i].getValue()));
    }
  }

  @Override
  public double getValue() {
    double ret = 0;
    for (int i = 0; i < features.length; i++) {
      ret += features[i].getValue() * weights[i];
    }
    return ret;
  }

  /**
   * Returns the features of the approximator.
   * 
   * @return the features
   */
  public Feature[] getFeatures() {
    return features;
  }

  /**
   * Sets the features of the approximator.
   * 
   * @param features the features to set
   */
  public void setFeatures(Feature[] features) {
    this.features = features;
  }

  /**
   * Returns the weights of the approximator.
   * 
   * @return the weights
   */
  @Override
  public double[] getWeights() {
    return weights;
  }

  /**
   * Sets the weights of the approximator.
   * 
   * @param weights the weights to set
   */
  public void setWeights(double[] weights) {
    this.weights = weights;
  }

  /**
   * Sets a single weight to the specified value.
   * 
   * @param index The index of the weight
   * @param weight The new weight value
   */
  public void setWeight(int index, double weight) {
    weights[index] = weight;
  }

  public double getWeight(int index) {
    return weights[index];
  }

  @Override
  public String toString() {
    String ret = "";
    for (Feature f : features) {
      ret += "|" + f.getValue() + "|";
    }

    ret += "\n";
    for (double w : weights) {
      ret += "|" + w + "|";
    }
    return ret;
  }
}
