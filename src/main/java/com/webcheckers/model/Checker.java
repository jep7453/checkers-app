package com.webcheckers.model;

public class Checker {

  /** Represents a single checker piece entity.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private Color color;
  private Type type;

  /** Creates a new Checker object (single type) of the specified color.
   * @param color       The color of the checker piece.
   */
  public Checker(Color color) {
    this.color = color;
    this.type = Type.SINGLE;
  }

  /** Creates a new Checker object of the specified color and type.
   * @param color       The color of the checker piece.
   * @param type        The type of the checker piece.
   */
  public Checker(Color color, Type type) {
    this.color = color;
    this.type = type;
  }

  /** Gets the color of this Checker piece.
   * @return The color of this checker piece.
   */
  public Color getColor() {
    return this.color;
  }

  /** Gets the type of this Checker piece.
   * @return The type of this checker piece.
   */
  public Type getType() {
    return this.type;
  }

  /** The color of a Checker piece. */
  public enum Color {
    RED,
    WHITE
  }

  /** The type of a Checker piece. */
  public enum Type {
    SINGLE,
    KING
  }

}
