package beans;

/**
 * 
 * @author giorgospetkakis
 *
 */
public class RemovalMove extends Move {

  private Cell cell;

  private Piece piece;

  public RemovalMove(Cell cell) {
    this.cell = cell;
    this.piece = cell.top();
  }

  /**
   * Returns the cell the move removes a piece from.
   * 
   * @return The cell on the board to remove from
   */
  public Cell getCell() {
    return this.cell;
  }

  @Override
  public AdditionMove getInverse() {
    return new AdditionMove(cell, piece);
  }
}
