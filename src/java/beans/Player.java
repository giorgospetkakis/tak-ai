package beans;

public class Player implements Comparable<Player> {

  /**
   * The current number of initialized players.
   */
  private static int playerCount = 0;

  public static final int DUMMY = 0;
  public static final int HUMAN = 1;
  public static final int ALPHA_BETA = 2;
  public static final int REINF_LEARNING = 3;

  /**
   * A unique player id.
   */
  private int id;

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
   * Default player constructor. Creates a dummy player.
   */
  public Player() {
    this.id = ++playerCount;
    this.name = "Dummy_" + id;
    this.playerType = 0;
  }

  /**
   * Quick constructor for different player types.
   * 
   * @param playerType The type of player being initialized
   */
  public Player(int playerType) {
    this.playerType = playerType;
    this.name = playerCount == 0 ? "White" : "Black";
    this.id = ++playerCount;
  }
  
  /**
   * Full constructor for different player types with specified names.
   * 
   * @param playerType The type of player being initialized
   */
  public Player(String name, int playerType) {
    this.playerType = playerType;
    this.name = name != null && !name.isEmpty() ? name : playerCount == 0 ? "White" : "Black";
    this.id = ++playerCount;
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
    if (this.id == p2.id) {
      return 1;
    }
    return 0;
  }
}
