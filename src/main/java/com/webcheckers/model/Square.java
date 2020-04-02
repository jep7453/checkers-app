package com.webcheckers.model;

public class Square {

  /** Represents a single checker square entity.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private Color color;      /** The color of this square. */
  private Checker checker;  /** The Checker piece on this square, if any. */
  private int rank; /** the rank where it is located */
  private  int file; /** the file where it is located */

  /** Creates a new Square object with no Checker piece on it.
   * @param color       The color of the Square.
   */
  public Square(Color color, int rank, int file) {
    this.color = color;
    this.checker = null;
    this.rank = rank;
    this.file = file;
  }

  /** Creates a new Square object with a Checker piece on it.
   * @param color       The color of the Square.
   * @param checker     The Checker piece to place on this Square.
   */
  public Square(Color color, Checker checker) {
    this.color = color;
    this.checker = checker;
  }

  /** Gets the color of this Square.
   * @return The color of this Square.
   */
  public Color getColor() {
    return this.color;
  }

  /** Gets the Checker piece currently on this Square.
   * @return The Checker on this Square, or null if this Square is empty.
   */
  public Checker getChecker() {
    return this.checker;
  }

  /** Checks if this Square has a Checker on it.
   * @return True if this Square has a Checker on it, otherwise false.
   */
  public boolean hasChecker() {
    return this.checker == null ? false : true;
  }

  /** Puts the Checker piece on this Square. If this Square is already
   * occupied, then it replaces the piece that is already on the Square.
   * @param checker     The Checker to put on this Square.
   */
  public void setChecker(Checker checker) {
    this.checker = checker;
  }

  public enum Color {
    LIGHT,
    DARK
  }

}

