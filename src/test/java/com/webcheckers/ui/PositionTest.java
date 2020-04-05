package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/***
 * This is unit test for Logic from Position Class.
 * @author Jonathan Pofcher <jep7453rit.edu>
 */
class PositionTest {


  private Position position1;
  private Position position2;
  private Position position3;
  private String four = "four";

  @Test
  void PositionEquals() {
    position1 = new Position(2, 3);
    position2 = new Position(4, 4);
    position3 = new Position(2, 3);
    assertFalse(position1.equals(position2));
    assertTrue(position1.equals(position3));
    assertFalse(position1.equals(four));
  }
}