package beans;

/**
 * A Java bean that represents a Tak Capstone piece.
 * 
 * @author giorgospetkakis
 *
 */
public class Capstone extends Piece {

  /**
   * Basic Capstone constructor.
   * @param owner The owner of the Capstone piece
   */
  public Capstone(Player owner) {
    super(owner);
  }

  /**
   * Returns false, as Capstones cannot be Standing Stones.
   */
  @Override
  public boolean isStandingStone() {
    return false;
  }
}
