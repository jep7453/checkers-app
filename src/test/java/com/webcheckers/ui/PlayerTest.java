package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/***
 * This is unit test for Logic from Player Class.
 * @author Jonathan Pofcher <jep7453rit.edu>
 */
class PlayerTest {


  private Player player1;
  private Player player2;
  private Player player3;
  private String four = "four";

  @Test
  void playerEquals() {
    player1 = new Player("red");
    player2 = new Player("white");
    player3 = new Player("red");
    assertFalse(player1.hashCode()==player2.hashCode());
    assertTrue(player1.hashCode()==player3.hashCode());
    assertFalse(player1.equals(four));
  }
}
