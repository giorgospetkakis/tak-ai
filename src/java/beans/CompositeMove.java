package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeMove extends Move {

  private List<MovementMove> moves;

  /**
   * Empty constructor for fast instantiation.
   */
  public CompositeMove() {
    this.moves = new ArrayList<MovementMove>();
  }

  /**
   * Creates a Composite move.
   * 
   * @param moves The moves to be added.
   */
  public CompositeMove(List<MovementMove> moves) {
    this.moves = moves;
  }


  @Override
  public CompositeMove getInverse() {
    ArrayList<MovementMove> mv = new ArrayList<MovementMove>();
    Iterator<MovementMove> i = this.moves.iterator();
    while (i.hasNext()) {
      MovementMove m = i.next();
      mv.add(0, m.getInverse());
    }
    return new CompositeMove(mv);
  }

  /**
   * Adds a move to the end of the composite move.
   * 
   * @param move The move to be added
   */
  public void add(MovementMove move) {
    moves.add(move);
  }

  public List<MovementMove> getMoves() {
    return moves;
  }

  /**
   * Returns a String representation of the Composite Move.
   */
  public String toString() {
    String ret = "Composite Move: ";
    for (MovementMove m : moves) {
      ret += m.toString() + ", ";
    }
    return ret.substring(0, ret.length() - 2);
  }
}
