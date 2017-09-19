package beans;

public abstract class Piece {

  protected Player owner;
  
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
