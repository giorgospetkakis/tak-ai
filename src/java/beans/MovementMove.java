package beans;

public class MovementMove extends Move {
  
  private Cell src, target;
  
  private int stackSize;
  
  public MovementMove(Cell src, Cell target, int stackSize) {
    this.setSource(src);
    this.setTarget(target);
    this.setStackSize(stackSize);
  }
  
  @Override
  public MovementMove getInverse(){
    return new MovementMove(target, src, stackSize);
  }

  /**
   * @return the cell
   */
  public Cell getSource() {
    return src;
  }

  /**
   * @param cell the cell to set
   */
  public void setSource(Cell src) {
    this.src = src;
  }
  
  /**
   * @return the cell
   */
  public Cell getTarget() {
    return target;
  }

  /**
   * @param cell the cell to set
   */
  public void setTarget(Cell target) {
    this.target = target;
  }

  /**
   * @return the stackSize
   */
  public int getStackSize() {
    return stackSize;
  }

  /**
   * @param stackSize the stackSize to set
   */
  public void setStackSize(int stackSize) {
    this.stackSize = stackSize;
  }
  
  @Override
  public String toString() {
    return "Move stack of size " + stackSize + " from " + src.toStringShrt() + " to " + target.toStringShrt();
  }
}
