package beans;

/**
 * A Java bean representation of a Tak Stone piece.
 * 
 * @author giorgospetkakis
 *
 */
public class Stone extends Piece {
  
  /**
   * Creates a new Stone piece for the specified Player.
   * Defaults to non-standing stones
   * @param owner The player that owns the piece
   */
  public Stone(Player owner) {
    super(owner);
    setStandingStone(false);
  }
  
  /**
   * Creates a new Stone piece for the specified Player.
   * @param owner The player that owns the piece
   * @param isStandingStone set to true if creating a Standing Stone
   */
  public Stone(Player owner, boolean isStandingStone) {
    super(owner);
    setStandingStone(isStandingStone);
    setWasStandingStone(isStandingStone);
  }
  
  /**
   * @return true is piece is a Standing Stone.
   */
  @Override
  public boolean isStandingStone() {
    return isStandingStone;
  }
  
  @Override
  public boolean wasStandingStone() {
    return wasStandingStone;
  }
}
