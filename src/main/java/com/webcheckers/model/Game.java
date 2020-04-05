package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

  /** Represents a checker Game.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private Player redPlayer;     /** The RED Player of this game. */
  private Player whitePlayer;   /** The WHITE Player of this game. */
  private Checkerboard board;   /** The Checkerboard this game is played on. */
  private List<Move> moves = new ArrayList<>(); /** A list of moves made, to backup */

  Player currentPlayer;         /** The Player whose turn it currently is. */

  private Player winner;

  /** Creates a new Game object with the specified players.
   * @param redPlayer   The Player to play as red.
   * @param whitePlayer The Player to play as white.
   */
  public Game(Player redPlayer, Player whitePlayer) {
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.board = new Checkerboard("noMovesRed.txt");
    this.currentPlayer = redPlayer;
  }

  /** Gets the Player whose turn it currently is.
   * @return The Player whose turn it currently is.
   */
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  /** Gets the Player playing as red.
   * @return The Player playing as red.
   */
  public Player getRedPlayer() {
    return this.redPlayer;
  }

  /** Gets the Player playing as white.
   * @return The Player playing as white.
   */
  public Player getWhitePlayer() {
    return this.whitePlayer;
  }

  /** Gets the Checkerboard this Game is being played on.
   * @return The Checkerboard this game is being played on.
   */
  public Checkerboard getBoard() {
    return this.board;
  }

  public Move.Type isValidMove(Move move) {
    return board.isValidMove(move);

  }

  public void makeMove(Move move) {
    board.makeMove(move);
    moves.add(move);
  }

  public Player currentPlayer() {
    return currentPlayer;
  }

  public void backUpMove() {
    board.backUpMove(moves.get(moves.size()-1));
    moves.remove(moves.size()-1);
  }

  public void switchPlayer() {
    moves.clear();
    if(currentPlayer.equals(whitePlayer)) {
      currentPlayer=redPlayer;
    }
    else {
      currentPlayer=whitePlayer;
    }
  }

  /**
   * Setter for checkerbaord
   *
   * @param board
   */
  public void setBoard(Checkerboard board) {
    this.board = board;
  }


  public boolean isValidTurn() {

    Move lastMove = moves.get(moves.size()-1);
    if(moves.get(0).getType()==Move.Type.JUMP) {
      return jumpValidation(lastMove);
    }
    else {
      return singleValidation(lastMove);
    }
  }

  public boolean jumpValidation(Move lastMove) {
    // Checks if a simple move is made in the same turn as a jump move
      for (Move move : moves) {
        if (move.getType().equals(Move.Type.SINGLE)) {
          return false;
        }
      }
    // Checks to make sure you make a multiple move if you can

      Square lastSquare = board.getSquare(lastMove.getEnd().getRow(),lastMove.getEnd().getCell());
      Checkerboard newBoard = board;
      if(currentPlayer.equals(redPlayer)) {
        lastSquare = board.getSquare(7-lastMove.getEnd().getCell(),7-lastMove.getEnd().getRow());
        newBoard=board.reverseBoard();
      }
      if(newBoard.pieceCanJump(lastSquare)) {
        return false;
      }
      return true;
  }

  public static void printBoard(Checkerboard board) {
    Square square;
    String col;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        square = board.getSquare(i, j);
        if(square.hasChecker())
          if(square.getChecker().getColor() == Checker.Color.RED)
            col = "R";
          else
            col = "W";
        else
          col = " ";

        System.out.print(String.format("| %s ", col));

      }
      System.out.println("|");
    }
    System.out.println();
  }

  public boolean singleValidation(Move lastMove) {
    //Checks if multiple simple moves are made
    if (moves.size() > 1) {
      return false;
    }
    // Checks that a jump wasn't a possible move
    Move reverseMove = new Move(lastMove.getEnd(),lastMove.getStart());
    board.makeMove(reverseMove);
    if(playerCanJump(currentPlayer)) {
      board.makeMove(lastMove);
      System.out.println("Jump available somewhere");
      return false;
    }
    board.makeMove(lastMove);
    return true;



  }

  /**
   * Check if a player can jump
   * @param player the player to check for
   * @return true if the can false if no
   */
  public boolean playerCanJump(Player player) {
    // assume player can move
    boolean canMove = false;
    Square tempSquare;
    Checkerboard checkerboard = getBoard();

    // player is white player
    if (player.equals(whitePlayer)) {
      if (checkerboard.allPiecesCaptured(Checker.Color.WHITE)) {
        return (false);
      }

      // iterate through board
      for (int i = 0; i < Checkerboard.NUM_RANKS; i++) {
        for (int j = 0; j < Checkerboard.NUM_FILES; j++) {
          // get square
          tempSquare = checkerboard.getSquare(i, j);
          // if the squares exists and has a white checker
          if (tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.WHITE) {
            canMove |= checkerboard.pieceCanJump(tempSquare);
          }
        }
      }
    }
    // else the red player so flip the board
    else{
      if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
        return (false);
      }
      printBoard(checkerboard);
      Checkerboard flippedBoard = checkerboard.reverseBoard();
      printBoard(flippedBoard);
      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = flippedBoard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            canMove |= flippedBoard.pieceCanJump(tempSquare);
          }
        }
      }
      flippedBoard = null;
    }

    return (canMove);
  }

    /**
     * Check if a player can move
     * @param player the player to check for
     * @return true if the can false if no
     */
    public boolean playerCanMove(Player player){
      // assume player can move
      boolean canMove = false;
      Square tempSquare;
      Checkerboard checkerboard = getBoard();

      // player is white player
      if(player.equals(whitePlayer)){
        if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
          return (false);
        }

        // iterate through board
        for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
          for(int j = 0; j < Checkerboard.NUM_FILES; j++){
            // get square
            tempSquare = checkerboard.getSquare(i,j);
            // if the squares exists and has a white checker
            if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.WHITE){
              canMove |= checkerboard.pieceCanMove(tempSquare);
            }
          }
        }
      }

    // else the red player so flip the board
    else{
      if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
        return (false);
      }
      Checkerboard flippedBoard = checkerboard.reverseBoard();
      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = flippedBoard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            canMove |= flippedBoard.pieceCanMove(tempSquare);
          }
        }
      }
      flippedBoard = null;
    }

    return (canMove);
  }

  /**
   * Check if the game has been won by pieces captured
   * or no moves
   * @return true if won, false if not
   */
  public boolean isGameWon(){
    boolean won = false;
    if(board.allPiecesCaptured(Checker.Color.RED)) {
      winner = whitePlayer;
      won = true;
    }
    if(board.allPiecesCaptured(Checker.Color.WHITE)) {
      winner = redPlayer;
      won = true;
    }
    if(!playerCanMove(redPlayer)) {
      winner = whitePlayer;
      won = true;
    }
    if(!playerCanMove(whitePlayer)) {
      winner = redPlayer;
      won = true;
    }
    return (won);
  }

}

