package com.webcheckers.model;

public class Player {

  /** Represents a player entity in a checker game.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private String name;  /** The name of the player. */

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
    return new String(this.name);
  }

}

