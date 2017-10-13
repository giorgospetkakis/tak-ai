package beans;

import java.util.Stack;

/**
 * A Java bean representation of a Tak Board Cell.
 * 
 * @author giorgospetkakis
 *
 */
public class Cell {

  public static final int NORTH = 0;
  public static final int SOUTH = 1;
  public static final int EAST = 2;
  public static final int WEST = 3;

  private Cell north;
  private Cell south;
  private Cell west;
  private Cell east;

  private Stack<Piece> pieces;

  private byte xpos;
  private byte ypos;

  private boolean visited;

  /**
   * Basic Cell constructor- places the cell in the specified x and y positions.
   * 
   * @param xpos the x coordinate of the cell
   * @param ypos the y coordinate of the cell
   */
  public Cell(byte xpos, byte ypos) {
    this.xpos = xpos;
    this.ypos = ypos;

    this.pieces = new Stack<Piece>();
  }

  /**
   * Returns the cell 'North' of <code> this </code>.
   * 
   * @return the cell to the north
   */
  public Cell getNorth() {
    return north;
  }

  /**
   * Sets the given cell to be north north of <code> this </code>.
   * 
   * @param north The cell to set to north
   */
  public void setNorth(Cell north) {
    this.north = north;
  }

  /**
   * Returns the cell 'South' of <code> this </code>.
   * 
   * @return the cell to the south
   */
  public Cell getSouth() {
    return south;
  }

  /**
   * Sets the given cell to be south of <code> this </code>.
   * 
   * @param south The cell to set to south
   */
  public void setSouth(Cell south) {
    this.south = south;
  }

  /**
   * Returns the cell 'West' of <code> this </code>.
   * 
   * @return the cell to the west
   */
  public Cell getWest() {
    return west;
  }

  /**
   * Sets the given cell to be west south of <code> this </code>.
   * 
   * @param west The cell to set to west
   */
  public void setWest(Cell west) {
    this.west = west;
  }

  /**
   * Returns the cell 'East' of <code> this </code>.
   * 
   * @return the cell to the east
   */
  public Cell getEast() {
    return east;
  }

  /**
   * Sets the given cell to be east of <code> this </code>.
   * 
   * @param east The cell to set to east
   */
  public void setEast(Cell east) {
    this.east = east;
  }

  /**
   * Returns the Stack of pieces in the cell.
   * 
   * @return the pieces in the cell
   */
  public Stack<Piece> getPieces() {
    return pieces;
  }

  /**
   * Sets the Stack of pieces.
   * 
   * @param pieces the stack of pieces to set
   */
  public void setPieces(Stack<Piece> pieces) {
    this.pieces = pieces;
  }

  /**
   * The x position of the cell.
   * 
   * @return the x position
   */
  public byte getXpos() {
    return xpos;
  }

  /**
   * Sets the x position of the cell.
   * 
   * @param xpos the x position to set
   */
  public void setXpos(byte xpos) {
    this.xpos = xpos;
  }

  /**
   * Returns the y position of the cell.
   * 
   * @return the y position
   */
  public byte getYpos() {
    return ypos;
  }

  /**
   * Sets the y position of the cell.
   * 
   * @param ypos the y position to set
   */
  public void setYpos(byte ypos) {
    this.ypos = ypos;
  }

  /**
   * Returns true if the cell has been visited. For use with BFS-Dijkstra algorithms
   * 
   * @return the visited
   */
  public boolean isVisited() {
    return visited;
  }

  /**
   * Sets the cell to (un)visited.
   * 
   * @param visited whether the cell has been visited
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  /**
   * Returns the cell's current controlling player.
   * 
   * @return the player that controls the cell
   */
  public Player getOwner() {
    return this.top().getOwner();
  }

  /**
   * Returns the top piece of the cell.
   * 
   * @return the top piece
   */
  public Piece top() {
    return pieces.peek();
  }

  /**
   * Returns whether the cell is empty.
   * 
   * @return true if the cell is empty.
   */
  public boolean isEmpty() {
    return pieces.isEmpty();
  }

  /**
   * Return the cell in the given direction.
   * 
   * @param dir A number 0-3
   * @return the cell in the given direction.
   */
  public Cell getDirection(int dir) {
    switch (dir) {
      case NORTH: {
        return this.north;
      }
      case SOUTH: {
        return this.south;
      }
      case EAST: {
        return this.east;
      }
      case WEST: {
        return this.west;
      }
      default: {
        return null;
      }
    }
  }

  /**
   * An abbreviated version of toString. Returns only the cell coordinates
   * 
   * @return The coordinates of the cell in readable format
   */
  public String toStringShrt() {
    return "("  + xpos + "," +  + ypos + ")";
  }
  
  /**
   * An abbreviated version of toString. Returns only the cell coordinates
   * 
   * @return The coordinates of the cell in readable format
   */
  public String toStringDisplay() {
    return "("  + (char)('A' + (xpos)) + "," +  + (ypos + 1) + ")";
  }

  /**
   * Returns a String representation of the cell.
   * 
   * @return the cell's String representation in full
   */
  @SuppressWarnings("all")
  public String toString() {
    return "\n" + "X=" + xpos + "\n" + "Y=" + ypos + "\n" + "Size" + pieces.size() + "\n"
        + "Visited " + visited + "\n" + "N " + ((north == null ? "null" : north.toStringShrt()))
        + "\n" + "S " + ((south == null ? "null" : south.toStringShrt())) + "\n" + "W "
        + ((west == null ? "null" : west.toStringShrt())) + "\n" + "E "
        + ((east == null ? "null" : east.toStringShrt()));
  }
}
