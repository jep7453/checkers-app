package com.webcheckers.model;

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

  /**
   * Check if a player can moce
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
          tempSquare = board.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            pieceCount++;
            canMove &= board.pieceCanMove(tempSquare);
          }
        }
      }
    }

    // make sure we aren't falsely returning true
    if(pieceCount == 0){
      return (false);
    }

    return (canMove);

  }

}

