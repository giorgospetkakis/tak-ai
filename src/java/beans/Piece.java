package beans;

public abstract class Piece {

  protected Player owner;
  protected boolean isStandingStone;

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
   * @return true is piece is a Standing Stone.
   */
  public boolean isStandingStone() {
    return isStandingStone;
  }
}
