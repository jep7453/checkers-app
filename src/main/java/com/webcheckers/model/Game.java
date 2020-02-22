package com.webcheckers.model;

public class Game {

  /** Represents a checker Game.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private Player redPlayer;     /** The RED Player of this game. */
  private Player whitePlayer;   /** The WHITE Player of this game. */
  private Checkerboard board;   /** The Checkerboard this game is played on. */

  /** Creates a new Game object with the specified players.
   * @param redPlayer   The Player to play as red.
   * @param whitePlayer The Player to play as white.
   */
  public Game(Player redPlayer, Player whitePlayer) {
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
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

}

