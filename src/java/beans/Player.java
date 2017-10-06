package beans;

/**
 * An abstract Java bean representation of a Tak Player.
 * 
 * @author giorgospetkakis
 *
 */
public class Player implements Comparable<Player> {

  /**
   * The current number of initialized players.
   */
  private static long playerCount = 0;

  public static final int DUMMY = 0;
  public static final int HUMAN = 1;
  public static final int ALPHA_BETA = 2;
  public static final int REINF_LEARNING = 3;

  /**
   * The player's name. Defaults to black / white.
   */
  private String name;

  /**
   * The type of player in control. 0 for dummy 1 for human 2 for alpha-beta AI 3 for reinforcement
   * learning AI
   */
  private int playerType;

  /**
   * The number of available default stones.
   */
  private int stonesAvailable;

  /**
   * The number of available Capstones.
   */
  private int capstonesAvailable;

  /**
   * Default player constructor. Creates a dummy player.
   */
  public Player() {
    this.setName(playerCount++ % 2 == 0 ? "White" : "Black");
    this.setPlayerType(0);
  }

  /**
   * Quick constructor for different player types.
   * 
   * @param playerType The type of player being initialized
   */
  public Player(int playerType) {
    this.setName(playerCount++ % 2 == 0 ? "White" : "Black");
    this.setPlayerType(playerType);
  }

  /**
   * Compares two players by their unique id.
   * 
   * @return 0 if the players are different
   *         <p>
   *         1 if the players are the same
   *         </p>
   */
  @Override
  public int compareTo(Player p2) {
    if (this.name == p2.name) {
      return 1;
    }
    return 0;
  }

  /**
   * Returns the Player's current name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the Player's name to a new value.
   * 
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the numeric value of the Player.
   * 
   * @return the playerType
   */
  public int getPlayerType() {
    return playerType;
  }

  /**
   * Sets the type of player.
   * 
   * @param playerType the playerType to set
   */
  private void setPlayerType(int playerType) {
    this.playerType = playerType;
  }

  /**
   * The number of Stones available to this Player.
   * @return the stonesAvailable
   */
  public int getStonesAvailable() {
    return stonesAvailable;
  }

  /**
   * Sets the number of Stones available to this Player.
   * @param stonesAvailable the stonesAvailable to set
   */
  public void setStonesAvailable(int stonesAvailable) {
    this.stonesAvailable = stonesAvailable;
  }

  /**
   * The number of Capstones available to this Player.
   * @return the capstonesAvailable
   */
  public int getCapstonesAvailable() {
    return capstonesAvailable;
  }

  /**
   * Sets the number of Capstones available.
   * @param capstonesAvailable the capstonesAvailable to set
   */
  public void setCapstonesAvailable(int capstonesAvailable) {
    this.capstonesAvailable = capstonesAvailable;
  }

  /**
   * Returns a Stone, owned by this player, if any.
   * @param standing set to true if making a Standing Stone
   * @return the Stone, if available.
   */
  public Stone getStone(boolean standing) {
    if (stonesAvailable > 0) {
      stonesAvailable--;
      return new Stone(this, standing);
    } else {
      return null;
    }
  }

  /**
   * Returns a Capstone, owned by this player, if any.
   * @return the Capstone, if available
   */
  public Capstone getCapstone() {
    if (capstonesAvailable > 0) {
      capstonesAvailable--;
      return new Capstone(this);
    } else {
      return null;
    }
  }
}
