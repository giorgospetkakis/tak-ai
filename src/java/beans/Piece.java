package beans;

/**
 * An abstract Java bean representation of a Tak Piece.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class Piece {

  private Player owner;
  protected boolean isStandingStone;
  protected boolean wasStandingStone;
  
  /**
   * Basic Piece constructor.
   * @param owner The owner of the piece
   */
  public Piece(Player owner) {
    setOwner(owner);
  }
  
  /**
   * Abstract method that returns if a piece is a standing stone.
   */
  public abstract boolean isStandingStone();

  /**
   * Returns the piece owner.
   * @return the owner
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * @param owner the owner to set.
   */
  public void setOwner(Player owner) {
    this.owner = owner;
  }

  /**
   * @param isStandingStone true if piece is a standing stone.
   */
  public void setStandingStone(boolean isStandingStone) {
    this.isStandingStone = isStandingStone;
  }
  
  public void setWasStandingStone(boolean wasStandingStone) {
    this.wasStandingStone = wasStandingStone;
  }

  public abstract boolean wasStandingStone();
}
