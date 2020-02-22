package com.webcheckers.model;

public class Checkerboard {

  /** Represents a checkerboard entity.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private final int NUM_RANKS = 8;
  private final int NUM_FILES = 8;

  /** The squares on the board. */
  private Square[][] squares = new Square[NUM_RANKS][NUM_FILES]; 

  /** Populates the squares of the Checkerboard and places pieces in their
   * starting positions. */
  private void populateBoard() {
    for(int rank = 0; rank < NUM_RANKS; rank++) {
      for(int file = 0; file < NUM_FILES; file++) {
        Square square = new Square(
            (rank * 8 + file) % 2 == 0 ? Square.Color.LIGHT : Square.Color.DARK
            );
        if( square.getColor() == Square.Color.DARK && rank < 3 )
          squares[rank][file].setChecker(new Checker(Checker.Color.RED));
        if( square.getColor() == Square.Color.DARK && rank > 4 )
          squares[rank][file].setChecker(new Checker(Checker.Color.WHITE));
        squares[rank][file] = square;
      }
    }
  }

  /** Creates a new Checkerboard with all the Checkers in starting position. */
  public Checkerboard() {
    populateBoard();
  }

  /** Gets the Square at a rank and file.
   * @param rank        The rank of the Square to get.
   * @param file        The file of the Square to get.
   * @return The square at the rank and file, or null if the requested square
   *         does not exist.
   */
  public Square getSquare(int rank, int file) {
    if( rank < 0 || rank >= NUM_RANKS || file < 0 || file >= NUM_FILES )
      return null;
    else
      return squares[rank][file];
  }

}

