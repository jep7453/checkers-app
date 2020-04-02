package com.webcheckers.model;

/** Represents a checkerboard entity.
 *
 * @author Scott Court <sxc4981@rit.edu>
 * @author Chris Tremblay <cst1465@rit.edu>
 */
public class Checkerboard {

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
                ((rank + file) % 2 == 0 ? Square.Color.LIGHT : Square.Color.DARK), rank, file
            );
        if( square.getColor() == Square.Color.DARK && rank < 3 )
          square.setChecker(new Checker(Checker.Color.RED));
        if( square.getColor() == Square.Color.DARK && rank > 4 )
          square.setChecker(new Checker(Checker.Color.WHITE));
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

  /**
   *
   * @param square the square to check
   * @return true if it can move, false if not
   */
  public boolean pieceCanMove(Square square){
    // make sure there is a piece on the square
    if( !square.hasChecker() )
      return (false);

    // boolean can move

    // check if piece is a king or not
    Checker checker = square.getChecker();
    boolean isKing = checker.getType().equals(Checker.Type.KING);

    // get rank and file
    int rank = square.getRank();
    int file = square.getFile();

    // define temporary ranks and file to edit
    int tempRank, tempFile;

    // check for a simple move to the left
    //tempRank = rank + 1;
    //tempFile = file - 1;






    return (true);
  }

}

