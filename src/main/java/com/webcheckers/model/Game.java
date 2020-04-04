package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.internal.$Gson$Preconditions;
import com.webcheckers.util.Message;

import javax.swing.*;
import java.awt.*;

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

  /** Creates a new Game object with the specified players.
   * @param redPlayer   The Player to play as red.
   * @param whitePlayer The Player to play as white.
   */
  public Game(Player redPlayer, Player whitePlayer) {
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.board = new Checkerboard();
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
    if(currentPlayer==whitePlayer) {
      currentPlayer=redPlayer;
    }
    else {
      currentPlayer=whitePlayer;
    }
  }

  /**
   * Setter for checkerbaord
   * @param board
   */
  public void setBoard(Checkerboard board) {
    this.board = board;
  }


  public boolean isValidTurn() {
    //Checks if multiple simple moves are made
    if (moves.size() > 1 && moves.get(0).getType().equals(Move.Type.SINGLE)) {
      return false;
    }
    // Checks if a simple move is made in the same turn as a jump move
    if (moves.get(0).getType().equals(Move.Type.JUMP)) {
      for (Move move : moves) {
        if (move.getType().equals(Move.Type.SINGLE)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check if a player can move
   * @param player the player to check for
   * @return true if the can false if no
   */
  public boolean playerCanMove(Player player){
    // assume player can move
    boolean canMove = true;
    Square tempSquare;
    int pieceCount = 0; // make sure we aren't saying we can move with zero peices
    Checkerboard checkerboard = getBoard();

    // player is white player
    if(player.equals(whitePlayer)){

      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = checkerboard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.WHITE){
            pieceCount++;
            canMove &= checkerboard.pieceCanMove(tempSquare);
          }
        }
      }
    }

    // else the red player so flip the board
    else{
      Checkerboard flippedBoard = checkerboard.reverseBoard();
      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = flippedBoard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            pieceCount++;System.out.println(tempSquare.getRank() + " " + tempSquare.getFile() + " " + flippedBoard.pieceCanMove(tempSquare));
            canMove &= flippedBoard.pieceCanMove(tempSquare);
          }
        }
      }
      flippedBoard = null;
    }

    return (pieceCount > 0 && canMove);
  }

}

