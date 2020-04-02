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

    /*
    X -> the space jumping to
    C -> the piece being moved
    J -> the square being jumped
    This algorithm first checks for a simple jump move, in either direction. And if it is a
    king then it it checks behind as well
    |   |   |   |   |   |
    |   | X |   | X |   |
    |   |   | C |   |   |
    |   | X |   | X |   |
    |   |   |   |   |   |

    It then checks to see if a jump move is possible by going two spaces diagonally
    and checking the space being jumped isn't the same color as the current checker
    | X |   |   |   | X |
    |   | J |   | J |   |
    |   |   | C |   |   |
    |   | J |   | J |   |
    | X |   |   |   | X |
     */
    for(int i = 1; i < 3; i++){
      // calculate space being jumped to
      tempRank = rank + i;
      tempFile = file + i;

      // move diagonally upwards and to the right
      if(canMoveHelper(tempRank, tempFile, tempRank-1, tempFile-1,
              (i==2), checker.getColor()))
        return (true);

      // move diagonally upwards and to the left
      if(canMoveHelper(tempRank,-tempFile, tempRank-1, tempFile+1,
              (i==2), checker.getColor()))
        return (true);

      // move diagonally DOWNWARDS and to the right
      if(isKing && canMoveHelper(-tempRank, tempFile,-tempRank+1, tempFile-1,
              (i==2), checker.getColor()))
        return (true);

      // move diagonally DOWNWARDS and to the left
      if(isKing && canMoveHelper(-tempRank, -tempFile, -tempRank+1, -tempFile + 1,
              (i==2), checker.getColor()))
        return (true);
    }

    return (false);
  }

  /**
   * This algorithm checks to see if a piece can move to a certain spot.
   * There are two cases, checking for a simple move and checking for a jump move
   * Simple move:
   *     - Checks to make sure space the piece want to move to is on the board
   *     - if there is not a square there then return true
   * Jump move:
   *     - Checks to make sure it is possible to jump is possible without going off board
   *     - Checks to see if the checker that is being jumped over is the opposing color
   *
   * @param tempRank the rank attempting to jump to
   * @param tempFile the file attemption to jump to
   * @param jumpRank rank of a piece if its getting jumped
   * @param jumpFile file of a piece if it is getting jumped
   * @param jumpMove if there is a jump move
   * @param col the color of the current checker
   * @return of the piece can move to that square
   */
  private boolean canMoveHelper(int tempRank, int tempFile, int jumpRank, int jumpFile, boolean jumpMove, Checker.Color col){
    if(0 <= tempRank && tempRank < NUM_RANKS && 0 <= tempFile && tempFile < NUM_FILES ){
      if(jumpMove){
        Checker checker = squares[jumpRank][jumpFile].getChecker();
        if(checker.getColor().equals(col))
          return (false);
      }
      if(!squares[tempRank][tempFile].hasChecker())
        return (true);
    }
    return (false);
  }

}

