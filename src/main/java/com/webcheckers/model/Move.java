package com.webcheckers.model;

public class Move {

  /** Represents the move of a checker.
   *
   * @author Jonathan Pofcher <jep7453@rit.edu>
   */

  private Position  start;  /** Start position of the move. */
  private Position end; /** End position of the move */
  private Type type; /** Type of move */

  /** The type of a Checker piece. */
  public enum Type {
    INVALID,
    SINGLE,
    JUMP
  }

  /** Creates a move with a start and .
   * @param start
   * @param end
   */
  public Move(Position start,Position end) {
    this.start = start;
    this.end=end;
    this.type=type.INVALID;
  }

  /** Gets the start
   * @return The start.
   */
  public Position getStart() {
    return this.start;
  }

  /** Gets the end
   * @return The end.
   */
  public Position getEnd() {
    return this.end;
  }

  /** Gets the type
   * @return The type.
   */
  public Move.Type getType() {
    return type;
  }

  /** Sets the type
   */
  public void setType(Move.Type type) {
    this.type=type;
  }


}