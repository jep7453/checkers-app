package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeAll;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {


    private Game game;
    private Checkerboard move;

    @BeforeEach
    void setup() {
        game = new Game(new Player("Red"), new Player("White"));
        move = game.getBoard();
    }

    @Test
    void moveOutsideBoard() {
        //back
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(-4, 5))) == Move.Type.INVALID);
        //front
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(14, 4))) == Move.Type.INVALID);
        //left
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(4, 9))) == Move.Type.INVALID);
        //right
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(4, 9))) == Move.Type.INVALID);
    }

    @Test
    void moveOnItself() {
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(2, 1))) == Move.Type.INVALID);
    }

    @Test
    void moveOnOwnTeamsChecker() {
        //setup game position
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        //moveOnOwnTeamsChecker
        assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(3, 2))) == Move.Type.INVALID);
    }

    @Test
    void moveOnOtherChecker() {
        //setup game position
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));

        move.makeMove(new Move(new Position(3, 2), new Position(4, 1)));

        //moveOnOwnTeamsChecker
       // assertTrue(move.isValidMove(new Move(new Position(5, 2), new Position(4, 1))) == Move.Type.INVALID);
        //assertTrue(move.isValidMove(new Move(new Position(), new Position())) == Move.Type.INVALID);
    }

    @Test
    void moveOnWhiteSquare() {
        assertTrue(move.isValidMove(new Move(new Position(2, 3), new Position(3, 3))) == Move.Type.INVALID);
    }

    @Test
    void moveOnFarDarkSquare() {
        assertTrue(move.isValidMove(new Move(new Position(2, 3), new Position(4, 3))) == Move.Type.INVALID);
    }

    @Test
    void singleCheckerMovingBack() {
        //setup game position
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));

        assertTrue(move.isValidMove(new Move(new Position(3, 2), new Position(2, 3))) == Move.Type.INVALID);
    }

    @Test
    void singleCheckerMovingFront() {
        assertTrue(move.isValidMove(new Move(new Position(2, 3), new Position(3, 2))) == Move.Type.SINGLE);
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        assertTrue(move.getSquare(3,2).hasChecker());//added one
        assertTrue(move.isValidMove(new Move(new Position(5, 6), new Position(4, 7))) == Move.Type.SINGLE);
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));
    }

    @Test
    void singleCheckerJumping() {

        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));

        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));

        //jump
        assertTrue(move.isValidMove(new Move(new Position(5, 4), new Position(3, 2))) == Move.Type.JUMP);
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));
        //checker eliminated
   //        assertFalse(game.getBoard().getSquare(4, 3).hasChecker());
    }

    @Test
    void singleCheckerReachingEnd() {
        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        //jump
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));

        move.makeMove(new Move(new Position(1, 4), new Position(2, 3)));
        //jump
        move.makeMove(new Move(new Position(3, 2), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(5, 0), new Position(4, 1)));

        move.makeMove(new Move(new Position(0, 3), new Position(1, 2)));
        move.makeMove(new Move(new Position(1, 4), new Position(0, 3)));

      //  assertTrue(game.getBoard().getSquare(0, 3).getChecker().getType() == Checker.Type.KING);

    }

    @Test
    void kingCheckerMovingBack() {

        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        //jump
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));

        move.makeMove(new Move(new Position(1, 4), new Position(2, 3)));
        //jump
        move.makeMove(new Move(new Position(3, 2), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(5, 0), new Position(4, 1)));

        move.makeMove(new Move(new Position(0, 3), new Position(1, 2)));
        move.makeMove(new Move(new Position(1, 4), new Position(0, 3)));

        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        //king moving back
        assertTrue(move.isValidMove(new Move(new Position(0, 3), new Position(1, 4))) == Move.Type.SINGLE);

    }

    @Test
    void kingCheckerMovingFront() {

        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        //jump
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));

        move.makeMove(new Move(new Position(1, 4), new Position(2, 3)));
        //jump
        move.makeMove(new Move(new Position(3, 2), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(5, 0), new Position(4, 1)));

        move.makeMove(new Move(new Position(0, 3), new Position(1, 2)));
        move.makeMove(new Move(new Position(1, 4), new Position(0, 3)));

        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        //king moving back
        move.makeMove(new Move(new Position(0, 3), new Position(1, 4)));

        move.makeMove(new Move(new Position(3, 2), new Position(5, 0)));
        //king checker moving front
        assertTrue(move.isValidMove(new Move(new Position(1, 4), new Position(0, 3))) == Move.Type.SINGLE);
    }

    @Test
    void kingCheckerJunping() {
        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        //jump
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));

        move.makeMove(new Move(new Position(1, 4), new Position(2, 3)));
        //jump
        move.makeMove(new Move(new Position(3, 2), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(5, 0), new Position(4, 1)));

        move.makeMove(new Move(new Position(0, 3), new Position(1, 2)));
        move.makeMove(new Move(new Position(1, 4), new Position(0, 3)));//king

        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(4, 1), new Position(2, 3)));

        move.makeMove(new Move(new Position(0, 5), new Position(1, 4)));
        move.makeMove(new Move(new Position(2, 3), new Position(0, 5)));

        move.makeMove(new Move(new Position(2, 1), new Position(3, 2)));
       // assertTrue(move.isValidMove(new Move(new Position(0, 3), new Position(2, 1))) == Move.Type.JUMP);
        move.makeMove(new Move(new Position(0, 3), new Position(2, 1)));
        assertFalse(move.isValidMove(new Move(new Position(2, 1), new Position(4, 3))) == Move.Type.JUMP);
        move.makeMove(new Move(new Position(2, 1), new Position(4, 3)));
    }

    @Test
    void endOfGame() {

        assertFalse(move.hasGameWon());

        //setup
        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(5, 6), new Position(4, 7)));


        move.makeMove(new Move(new Position(3, 2), new Position(4, 3)));
        //jump
        move.makeMove(new Move(new Position(5, 4), new Position(3, 2)));

        move.makeMove(new Move(new Position(1, 4), new Position(2, 3)));
        //jump
        move.makeMove(new Move(new Position(3, 2), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(5, 0), new Position(4, 1)));

        move.makeMove(new Move(new Position(0, 3), new Position(1, 2)));
        move.makeMove(new Move(new Position(1, 4), new Position(0, 3)));//king

        move.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        move.makeMove(new Move(new Position(4, 1), new Position(2, 3)));

        move.makeMove(new Move(new Position(0, 5), new Position(1, 4)));
        move.makeMove(new Move(new Position(2, 3), new Position(0, 5)));

        move.makeMove(new Move(new Position(2, 1), new Position(3, 2)));
      //  assertTrue(move.isValidMove(new Move(new Position(0, 3), new Position(2, 1))) == Move.Type.JUMP);
        move.makeMove(new Move(new Position(0, 3), new Position(2, 1)));
     //   assertTrue(move.isValidMove(new Move(new Position(2, 1), new Position(4, 3))) == Move.Type.JUMP);
        move.makeMove(new Move(new Position(2, 1), new Position(4, 3)));

        move.makeMove(new Move(new Position(2, 5), new Position(3, 6)));
        move.makeMove(new Move(new Position(4, 7), new Position(2, 5)));

        move.makeMove(new Move(new Position(2, 7), new Position(3, 6)));
        move.makeMove(new Move(new Position(2, 5), new Position(4, 7)));

        move.makeMove(new Move(new Position(0, 1), new Position(1, 2)));
        move.makeMove(new Move(new Position(0, 5), new Position(2, 7)));

        move.makeMove(new Move(new Position(0, 7), new Position(1, 6)));
        move.makeMove(new Move(new Position(2, 7), new Position(0, 5)));

        move.makeMove(new Move(new Position(1, 2), new Position(2, 3)));
        move.makeMove(new Move(new Position(0, 5), new Position(1, 4)));

        move.makeMove(new Move(new Position(1, 0), new Position(2, 1)));
        move.makeMove(new Move(new Position(1, 4), new Position(3, 2)));
        move.makeMove(new Move(new Position(3, 2), new Position(1, 0)));

        //assertTrue(move.hasGameWon());
    }
}