package players.rl;

public class Feature {

  private static volatile double ext_save;

  private Runnable expression;

  public Feature(Runnable expression) {
    this.expression = expression;
  }
  
  /**
   * Sets the value of the external save variable.
   * TODO: TEST THIS A LOT
   * @param value the value the feature's anon method returns
   */
  public static void setValue(double value) {
    ext_save = value;
  }
  
  /**
   * Returns the value of the Feature for the current game.
   * @return the decimal value of the feature
   */
  public double getValue() {
    synchronized (this) {
      expression.run();
      return ext_save;
    }
  }
}
