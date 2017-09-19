package beans;

import java.util.Stack;

public class Cell {
  
  private Cell north;
  private Cell south;
  private Cell west;
  private Cell east;
  
  private Stack<Piece> pieces;
  
  private byte xpos;
  private byte ypos;
  
  private boolean visited;
  
  /**
   * Basic Cell constructor- places the cell in
   * the specified x and y positions.
   * @param xpos the x coordinate of the cell
   * @param ypos the y coordinate of the cell
   */
  public Cell(byte xpos, byte ypos) {
    this.xpos = xpos;
    this.ypos = ypos;
    
    this.pieces = new Stack<Piece>();
  }

  /**
   * @return the north
   */
  public Cell getNorth() {
    return north;
  }

  /**
   * @param north the north to set
   */
  public void setNorth(Cell north) {
    this.north = north;
  }

  /**
   * @return the south
   */
  public Cell getSouth() {
    return south;
  }

  /**
   * @param south the south to set
   */
  public void setSouth(Cell south) {
    this.south = south;
  }

  /**
   * @return the west
   */
  public Cell getWest() {
    return west;
  }

  /**
   * @param west the west to set
   */
  public void setWest(Cell west) {
    this.west = west;
  }

  /**
   * @return the east
   */
  public Cell getEast() {
    return east;
  }

  /**
   * @param east the east to set
   */
  public void setEast(Cell east) {
    this.east = east;
  }

  /**
   * @return the pieces
   */
  public Stack<Piece> getPieces() {
    return pieces;
  }

  /**
   * @param pieces the pieces to set
   */
  public void setPieces(Stack<Piece> pieces) {
    this.pieces = pieces;
  }

  /**
   * @return the xpos
   */
  public byte getXpos() {
    return xpos;
  }

  /**
   * @param xpos the xpos to set
   */
  public void setXpos(byte xpos) {
    this.xpos = xpos;
  }

  /**
   * @return the ypos
   */
  public byte getYpos() {
    return ypos;
  }

  /**
   * @param ypos the ypos to set
   */
  public void setYpos(byte ypos) {
    this.ypos = ypos;
  }

  /**
   * @return the visited
   */
  public boolean isVisited() {
    return visited;
  }

  /**
   * @param visited the visited to set
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }
  
  public Player getOwner() {
    return this.top().getOwner();
  }
  
  public Piece top() {
    return pieces.peek();
  }
  
  public String toStringShrt() {
    return "X=" + xpos + " Y=" + ypos;
  }
  
  /**
   * Returns a String representation of a cell.
   */
  public String toString() {
    return 
        "\n" +
        "X=" + xpos + "\n" +
        "Y=" + ypos + "\n" +
        "Size" + pieces.size() + "\n" +
        "Visited " + visited + "\n" +
        "N " + ((north == null ? "null" : north.toStringShrt())) + "\n" +
        "S " + ((south == null ? "null" : south.toStringShrt())) + "\n" +
        "W " + ((west == null ? "null" : west.toStringShrt())) + "\n" +
        "E " + ((east == null ? "null" : east.toStringShrt()));
  }
}
