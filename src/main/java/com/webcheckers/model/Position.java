package com.webcheckers.model;

public class Position {

  /** Represents the position of a space on the board.
   *
   * @author Jonathan Pofcher <jep7453@rit.edu>
   */

  private int row;  /** Row Index of this position. */
  private int cell; /** Cell Index of this position */

  /** Creates a new Position object at specified position.
   * @param row
   * @param cell
   */
  public Position(int row,int cell) {
    this.row = row;
    this.cell=cell;
  }

  /** Gets the row
   * @return The row.
   */
  public int getRow() {
    return this.row;
  }

  /** Gets the cell
   * @return The cell.
   */
  public int getCell() {
    return this.cell;
  }

  @Override
  public boolean equals(Object o) {
    Position that;
    if (!(o instanceof Position)) {
      return false;
    }
    that = (Position) o;
    return this.row==(that.row)&&this.cell==(that.cell);
  }


}
