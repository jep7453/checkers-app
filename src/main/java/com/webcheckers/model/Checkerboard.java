package com.webcheckers.model;


import static com.webcheckers.model.Checker.Color.*;
import static com.webcheckers.model.Checker.Type.SINGLE;

public class Checkerboard {

  /** Represents a checkerboard entity.
   *
   * @author Scott Court <sxc4981@rit.edu>
   * @author Jonathan Pofcher <jep7453@rit.edu>
   * @author  Kesa Abbas Lnu <kl3468@rit.edu>
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
            (rank + file) % 2 == 0 ? Square.Color.LIGHT : Square.Color.DARK
            );
        if( square.getColor() == Square.Color.DARK && rank < 3 )
          square.setChecker(new Checker(RED));
        if( square.getColor() == Square.Color.DARK && rank > 4 )
          square.setChecker(new Checker(WHITE));
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

  public Move.Type isValidMove(Move move)
  {
    //returns true if move is made and updates the checkers position in the checkerboard.
    //if game is won then winner is updated.
    //returns false if move cannot be made or the game is already over
    Position Start = move.getStart();
    Position End = move.getEnd();
    int rowS = Start.getRow();
    int colS = Start.getCell();
    int rowD = End.getRow();
    int colD= End.getCell();

    Square startChecker = getSquare(rowS,colS);
    Square endChecker = getSquare(rowD,colD);


    //check if start and dest are diagonally located
    if(Math.abs(rowS-rowD) != Math.abs(colS-colD)) return Move.Type.INVALID;

    //check if start has checkers and dest is vacant
    if(startChecker.getChecker() != null && endChecker.getChecker() == null){

      //check if checker color is RED and current player is RED
      if(startChecker.getChecker().getColor().equals(RED)){

        //checker is SINGLE
        if(startChecker.getChecker().getType() == SINGLE){

          //check if dest is away from player
          if(rowS < rowD){

            //check if we are jumping over one square
            if(rowD - rowS == 2) {
              //get the square jumped
              Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

              //square jumped must have checker of other player
              if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == WHITE) {
                return Move.Type.JUMP;
              }
            }
            //check if we are not jumping over squares{
            else if(rowD - rowS == 1){
                return Move.Type.SINGLE;
              }
            } else return Move.Type.INVALID;

          } else return Move.Type.INVALID;
        }
        //checker is KING
        else {

          //check if we are jumping over one square
          if(Math.abs(rowD - rowS) == 2){
            //get the square jumped
            Square squareJumped = getSquare((rowS+rowD)/2,(colS+colD)/2);

            //square jumped must have checker of other player
            if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == WHITE){
              return Move.Type.JUMP;
            }
          }
          //check if we are not jumping over squares{
          else if(Math.abs(rowD - rowS) == 1){
            return Move.Type.SINGLE;
          }else return Move.Type.INVALID;

        }
      }
    //check if checker color is WHITE and current player is WHITE
    if(startChecker.getChecker().getColor().equals(WHITE)){

      //checker is SINGLE
      if(startChecker.getChecker().getType() == SINGLE){

        //check if dest is away from player
        if(rowS > rowD){

          //check if we are jumping over one square
          if(rowD - rowS == -2) {
            //get the square jumped
            Square squareJumped = getSquare((rowS + rowD) / 2, (colS + colD) / 2);

            //square jumped must have checker of other player
            if (squareJumped != null && squareJumped.getChecker() != null && squareJumped.getChecker().getColor() == RED) {
              return Move.Type.JUMP;

            }
          }
          //check if we are not jumping over squares{
          else if(rowD - rowS == -1){
            return Move.Type.SINGLE;
          }
        } else return Move.Type.INVALID;

      }

    //checker is KING
    else {

      //check if we are jumping over one square
      if(Math.abs(rowD - rowS) == 2){
        //get the square jumped

        Square squareJumped = getSquare((rowS+rowD)/2,(colS+colD)/2);

        //square jumped must have checker of other player
        if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == RED){
          return Move.Type.JUMP;
        }
      }
      //check if we are not jumping over squares{
      else if(Math.abs(rowD - rowS) == 1){
        return Move.Type.SINGLE;
      }else return Move.Type.INVALID;

    } return Move.Type.INVALID;
  }


    return Move.Type.INVALID;
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

