package beans;

/**
 * An abstract Java bean representation of a Tak Piece.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class Piece {

  protected Player owner;
  
  /**
   * Basic Piece constructor.
   * @param owner The owner of the piece
   */
  public Piece(Player owner) {
    setOwner(owner);
  }
  
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
}
