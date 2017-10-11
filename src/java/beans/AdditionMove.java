package beans;

public class AdditionMove extends Move {

  private Cell cell;

  private Piece piece;

  public AdditionMove(Cell cell, Piece piece) {
    this.cell = cell;
    this.piece = piece;
  }

  /**
   * Return the piece that's being added.
   * 
   * @return the piece that's being added
   */
  public Piece getPiece() {
    if (this.piece instanceof Capstone) {
      return this.piece.getOwner().getCapstone();
    } else {
      return this.piece.getOwner().getStone(this.piece.isStandingStone());
    }
  }

  /**
   * Return the cell that is being modified.
   * 
   * @return The cell the piece is added to
   */
  public Cell getCell() {
    return this.cell;
  }

  @Override
  public RemovalMove getInverse() {
    return new RemovalMove(cell);
  }

  @Override
  public String toString() {
    String type = "flat-stone";
    if (piece instanceof Capstone) {
      type = "capstone";
    } else if (piece.isStandingStone()) {
      type = "standing-stone";
    }

    return "Add " + type + " to: " + cell.toStringShrt();
  }
}
