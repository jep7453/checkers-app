package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeAll;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/***
 * This is unit test for Move Logic from CheckerBoard Class.
 * @author Kesa Abbas Lnu <kl3468rit.edu>
 */
class SingleJumpTest {


    private Game game;
    private Checkerboard move;

    @BeforeEach
    void setup() {
        game = new Game(new Player("Red"), new Player("White"));
        move = game.getBoard();
    }

    @Test
    void invalidJump() {
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));

        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        SingleJump sj = new SingleJump(game);
        assertTrue(!sj.singleJumpWorked(5,4,3,2));
    }
}