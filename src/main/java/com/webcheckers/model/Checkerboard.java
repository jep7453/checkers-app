package com.webcheckers.model;


import static com.webcheckers.model.Checker.Color.*;
import static com.webcheckers.model.Checker.Type.KING;
import static com.webcheckers.model.Checker.Type.SINGLE;
import java.io.File;
import java.util.Scanner;

/** Represents a checkerboard entity.
 *
 * @author Scott Court <sxc4981@rit.edu>
 * @author Chris Tremblay <cst1465@rit.edu>
 * @author Jonathan Pofcher <jep7453rit.edu>
 * @auhor Kesa Abbas Lnu <kl3468@rit.edu>
 */
public class Checkerboard {

  public static final int NUM_RANKS = 8;
  public static final int NUM_FILES = 8;

  /**
   * The squares on the board.
   */
  private Square[][] squares = new Square[NUM_RANKS][NUM_FILES];

  /**
   * Creates a new Checkerboard with all the Checkers in starting position.
   */
  public Checkerboard() {
    populateBoard();
  }

  /**
   * If you need a custom board you can create a file with the pieces you need
   * See src/main/resources/testboard for more
   *
   * @param fp the file to test in resources file
   */
  public Checkerboard(String fp) {
    populateBoard(fp);
  }

  /**
   * Create a checkerboard based on a board already made
   *
   * @param board
   */
  private Checkerboard(Square[][] board) {
    this.squares = board;
  }

  /**
   * Populates the squares of the Checkerboard and places pieces in their
   * starting positions.
   */
  private void populateBoard() {
    for (int rank = 0; rank < NUM_RANKS; rank++) {
      for (int file = 0; file < NUM_FILES; file++) {
        Square square = new Square(
                ((rank + file) % 2 == 0 ? Square.Color.LIGHT : Square.Color.DARK), rank, file
        );
        if (square.getColor() == Square.Color.DARK && rank < 3)
          square.setChecker(new Checker(Checker.Color.RED));
        if (square.getColor() == Square.Color.DARK && rank > 4)
          square.setChecker(new Checker(Checker.Color.WHITE));
        squares[rank][file] = square;
      }
    }
  }

  private void populateBoard(String fp) {
    Scanner in;
    try {
      String current = new java.io.File(".").getCanonicalPath();
      String path = current + "/src/main/resources/testboards/";
      in = new Scanner(new File(path + fp));
      for (int rank = 0; rank < NUM_RANKS; rank++) {
        String line = in.nextLine();
        String[] pieces = line.split(",");

        for (int file = 0; file < NUM_FILES; file++) {
          Square square = new Square(
                  ((rank + file) % 2 == 0 ? Square.Color.LIGHT : Square.Color.DARK), rank, file
          );
          if (!pieces[file].trim().equals("")) {
            square.setChecker(makeChecker(pieces[file]));
          }

          squares[rank][file] = square;
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      populateBoard();
    }

  }

  /**
   * Make a checker based on the code in the text file
   * r -> red piece
   * w -> white piece
   * has k in it makes it a king
   *
   * @param checkerCode
   * @return
   */
  private Checker makeChecker(String checkerCode) {
    Checker.Color col;
    Checker.Type type;

    if (checkerCode.contains("k"))
      type = Checker.Type.KING;
    else
      type = Checker.Type.SINGLE;


    if (checkerCode.contains("r"))
      col = Checker.Color.RED;
    else
      col = Checker.Color.WHITE;

    Checker checker = new Checker(col, type);
    return (checker);
  }

  /**
   * Gets the Square at a rank and file.
   *
   * @param rank The rank of the Square to get.
   * @param file The file of the Square to get.
   * @return The square at the rank and file, or null if the requested square
   * does not exist.
   */
  public Square getSquare(int rank, int file) {
    if (rank < 0 || rank >= NUM_RANKS || file < 0 || file >= NUM_FILES)
      return null;
    else
      return squares[rank][file];
  }

  /**
   * @param square the square to check
   * @return true if it can move, false if not
   */
  public boolean pieceCanMove(Square square) {
    // make sure there is a piece on the square
    if (!square.hasChecker())
      return (false);

    // boolean can move

    // check if piece is a king or not
    Checker checker = square.getChecker();
    boolean isKing = checker.getType() == Checker.Type.KING;

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
    for (int i = 1; i < 3; i++) {
      // calculate space being jumped to
      tempRank = rank - i;
      tempFile = file + i;

      // move diagonally upwards and to the right
      if (canMoveHelper(tempRank, tempFile, rank - 1, file + 1,
              (i == 2), checker.getColor()))
        return (true);

      // move diagonally upwards and to the left
      if (canMoveHelper(tempRank, tempFile - (2 * i), rank - 1, file - 1,
              (i == 2), checker.getColor()))
        return (true);

      // move diagonally DOWNWARDS and to the right
      if (isKing && canMoveHelper(tempRank + (2 * i), tempFile, rank + 1, file + 1,
              (i == 2), checker.getColor()))
        return (true);

      // move diagonally DOWNWARDS and to the left
      if (isKing && canMoveHelper(tempRank + (2 * i), tempFile - (2 * i), rank + 1, file - 1,
              (i == 2), checker.getColor()))
        return (true);
    }
  public Move.Type isValidMove(Move move) {
    //returns true if move is made and updates the checkers position in the checkerboard.
    //if game is won then winner is updated.
    //returns false if move cannot be made or the game is already over
    Position Start = move.getStart();
    Position End = move.getEnd();
    int rowS = Start.getRow();
    int colS = Start.getCell();
    int rowD = End.getRow();
    int colD = End.getCell();

    Square startChecker = getSquare(rowS, colS);
    Square endChecker = getSquare(rowD, colD);


    //check if start and dest are diagonally located
    if (Math.abs(rowS - rowD) != Math.abs(colS - colD)) return Move.Type.INVALID;

    //check if start has checkers and dest is vacant
    if (startChecker.getChecker() != null && endChecker.getChecker() == null) {

      //check if checker color is RED and current player is RED
      if (startChecker.getChecker().getColor().equals(RED)) {

        //checker is SINGLE
        if (startChecker.getChecker().getType() == SINGLE) {

          //check if dest is away from player
          if (rowS < rowD) {

            //check if we are jumping over one square
            if (rowD - rowS == 2) {
              //get the square jumped
              Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

              //square jumped must have checker of other player
              if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == WHITE) {
                return Move.Type.JUMP;
              }
            }
            //check if we are not jumping over squares{
            else if (rowD - rowS == 1) {
              return Move.Type.SINGLE;
            }
          } else return Move.Type.INVALID;

        }

        //checker is KING
        else {

          //check if we are jumping over one square
          if (Math.abs(rowD - rowS) == 2) {
            //get the square jumped
            Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

            //square jumped must have checker of other player
            if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == WHITE) {
              return Move.Type.JUMP;
            }
          }
          //check if we are not jumping over squares{
          else if (Math.abs(rowD - rowS) == 1) {
            return Move.Type.SINGLE;
          } else return Move.Type.INVALID;

        }
      }
      //check if checker color is WHITE and current player is WHITE
      if (startChecker.getChecker().getColor().equals(WHITE)) {

        //checker is SINGLE
        if (startChecker.getChecker().getType() == SINGLE) {

          //check if dest is away from player
          if (rowS > rowD) {

            //check if we are jumping over one square
            if (rowD - rowS == -2) {
              //get the square jumped
              Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

              //square jumped must have checker of other player
              if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == RED) {
                return Move.Type.JUMP;

              }
            }
            //check if we are not jumping over squares{
            else if (rowD - rowS == -1) {
              return Move.Type.SINGLE;
            }
          } else return Move.Type.INVALID;

        }

        //checker is KING
        else {

          //check if we are jumping over one square
          if (Math.abs(rowD - rowS) == 2) {
            //get the square jumped

            Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

            //square jumped must have checker of other player
            if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == RED) {
              return Move.Type.JUMP;
            }
          }
          //check if we are not jumping over squares{
          else if (Math.abs(rowD - rowS) == 1) {
            return Move.Type.SINGLE;
          } else return Move.Type.INVALID;

        }
        return Move.Type.INVALID;
      } else return Move.Type.INVALID;
    } else return Move.Type.INVALID;
  }



  public void makeMove(Move move) {

    Position Start = move.getStart();
    Position End = move.getEnd();
    int rowS = Start.getRow();
    int colS = Start.getCell();
    int rowD = End.getRow();
    int colD= End.getCell();

    Square startChecker = getSquare(rowS,colS);
    Square endChecker = getSquare(rowD,colD);

    if(rowD==7&&startChecker.getChecker().getColor().equals(RED)||rowD==0&&startChecker.getChecker().getColor().equals(WHITE)) {
      startChecker.getChecker().setType(KING);
    }

    if(move.getType().equals(Move.Type.JUMP)) {
      Square squareJumped = getSquare((rowS+rowD)/2,(colS+colD)/2);
      squareJumped.setChecker(null);

  }
    endChecker.setChecker(startChecker.getChecker());
    startChecker.setChecker(null);
  }

  public void backUpMove(Move move) {
    Position Start = move.getStart();
    Position End = move.getEnd();
    int rowS = Start.getRow();
    int colS = Start.getCell();
    int rowD = End.getRow();
    int colD= End.getCell();

    Square startChecker = getSquare(rowS,colS);
    Square endChecker = getSquare(rowD,colD);

    if(move.getType().equals(Move.Type.JUMP)) {
      Square squareJumped = getSquare((rowS+rowD)/2,(colS+colD)/2);
      Checker.Color replaceColor;
      if(endChecker.getChecker().getColor()== WHITE) {
        replaceColor=RED;
      }
      else {
        replaceColor=WHITE;
      }
      Checker replaceChecker = new Checker(replaceColor,SINGLE);
      squareJumped.setChecker(replaceChecker);
    }
    startChecker.setChecker(endChecker.getChecker());
    endChecker.setChecker(null);
  }
}

    return (false);
  }

  /**
   * This algorithm checks to see if a piece can move to a certain spot.
   * There are two cases, checking for a simple move and checking for a jump move
   * Simple move:
   * - Checks to make sure space the piece want to move to is on the board
   * - if there is not a square there then return true
   * Jump move:
   * - Checks to make sure it is possible to jump is possible without going off board
   * - Checks to see if the checker that is being jumped over is the opposing color
   *
   * @param tempRank the rank attempting to jump to
   * @param tempFile the file attemption to jump to
   * @param jumpRank rank of a piece if its getting jumped
   * @param jumpFile file of a piece if it is getting jumped
   * @param jumpMove if there is a jump move
   * @param col      the color of the current checker
   * @return of the piece can move to that square
   */
  private boolean canMoveHelper(int tempRank, int tempFile, int jumpRank, int jumpFile, boolean jumpMove, Checker.Color col) {
    // check that piece attempting to move to is in bounds
    if (0 <= tempRank && tempRank < NUM_RANKS && 0 <= tempFile && tempFile < NUM_FILES) {

      // is this a jump move?
      if (jumpMove) {
        // get the checker that is getting jumped over
        Checker checker = squares[jumpRank][jumpFile].getChecker();

        // if it is the same color as the current checker
        if (checker.getColor() == col)
          return (false);
      }

      // is the square free where we are trying to move to?
      if (squares[tempRank][tempFile].hasChecker()) {
        return (false);
      } else
        return (true);
    }

    // all failed, cannot move
    return (false);
  }

  /**
   * Reverse the checker board to check if a red player can move
   *
   * @return the reversed board
   */
  public Checkerboard reverseBoard() {
    int rows = squares.length;
    int cols = squares[0].length;
    Square array[][] = new Square[rows][cols];
    for (int i = rows - 1; i >= 0; i--) {
      for (int j = cols - 1; j >= 0; j--) {
        int tempRank = squares[i][j].getRank();
        int tempFile = squares[i][j].getFile();
        array[rows - 1 - i][cols - 1 - j] = new Square(squares[i][j].getColor(), tempFile, tempRank);
        if (squares[i][j].hasChecker()) {
          array[rows - 1 - i][cols - 1 - j].setChecker(squares[i][j].getChecker());
        }
      }
    }
    return (new Checkerboard(array));
  }
}