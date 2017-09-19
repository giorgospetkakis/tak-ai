package beans;

public class Capstone extends Piece {

  public Capstone(Player owner) {
    super(owner);
  }
  
  @Override
  public boolean isStandingStone() {
    return false;
  }
}
