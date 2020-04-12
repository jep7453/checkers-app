package com.webcheckers.model;

public class Player {

  /** Represents a player entity in a checker game.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private String name;  /** The name of the player. */
  /** total played by player */
  private int totalGames;

  /* total games on by player */
  private int gamesWon;

  /** Creates a new Player object with the specified name.
   * @param name        The name of the Player.
   */
  public Player(String name) {
    this.name = name;
  }

  /** Gets the name of the Player.
   * @return The name of the Player.
   */
  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object o) {
    Player that;
    if (!(o instanceof Player)) {
      return false;
    }
    that = (Player) o;
    return this.name.equals(that.name);
  }

  /**
   * Tell the player they won the game
   */
  public void wonGame(){
    totalGames++;
    gamesWon++;
  }

  /**
   * Tell player they lost a game
   */
  public void lostGame(){
    totalGames++;
  }

  /**
   * Get the win rate of the player
   * @return
   */
  public int getWinRate(){
    if(totalGames == 0){
      return 0;
    }
    return (int)(((gamesWon + 0.0)/totalGames)*100);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}

