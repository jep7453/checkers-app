package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeAll;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/***
 * This is unit test for Logic from CheckerBoard Class.
 * @author Jonathan Pofcher <jep7453rit.edu>
 */
class CheckerBoardTest {


  private Game game;
  private Checkerboard board;

  @BeforeEach
  void setup() {
    game = new Game(new Player("Red"), new Player("White"));
    board= game.getBoard();
  }

  @Test
  void backUpMove() {
    //set up
    Move move = new Move(new Position(2, 3), new Position(3, 2));
    move.setType(Move.Type.SINGLE);
    Square squareChecker = board.getSquare(2,3);
    Square squareEmpty= board.getSquare(3,2);
    game.makeMove(move);
    game.backUpMove();
    assertTrue(board.getSquare(2,3).equals(squareChecker));
    assertTrue(board.getSquare(3,2).equals(squareEmpty));
    game.printBoard(board);
    }

  @Test
  void backUpJump() {
    //set up
    game.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
    game.makeMove(new Move(new Position(4,5),new Position(4,3)));
    Move move = (new Move(new Position(3,2),new Position(4,5)));
    move.setType(Move.Type.JUMP);
    Square squareChecker = board.getSquare(3,2);
    Square squareEmpty= board.getSquare(4,5);
    Square squareJumped= board.getSquare(4,3);
    game.makeMove(move);
    game.backUpMove();
    assertTrue(board.getSquare(3,2).equals(squareChecker));
    assertTrue(board.getSquare(4,5).equals(squareEmpty));
    assertTrue(board.getSquare(4,3).equals(squareJumped));
  }

  @Test
  void redKingJumps(){
    board=new Checkerboard("jumpRedKing.txt");
    game.setBoard(board);
    assertTrue(game.isValidMove(new Move(new Position(3,4),new Position(5,6))).equals(Move.Type.JUMP));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,6))).equals(Move.Type.JUMP));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(5,2))).equals(Move.Type.JUMP));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,2))).equals(Move.Type.JUMP));

    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(4,5))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,5))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(4,3))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,3))).equals(Move.Type.INVALID));

  }

  @Test
  void redKingMoves(){
    board=new Checkerboard("moveRedKing.txt");
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(4,5))).equals(Move.Type.SINGLE));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(2,5))).equals(Move.Type.SINGLE));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(4,3))).equals(Move.Type.SINGLE));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(2,3))).equals(Move.Type.SINGLE));

    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(5,6))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,6))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(5,2))).equals(Move.Type.INVALID));
    assertTrue(board.isValidMove(new Move(new Position(3,4),new Position(1,2))).equals(Move.Type.INVALID));
  }

  }
