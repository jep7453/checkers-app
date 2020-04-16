package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeAll;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/***
 * This is unit test for Logic from Game Class.
 * @author Jonathan Pofcher <jep7453rit.edu>
 */
class GameTest {


  private Game game;
  private Checkerboard board;
  private Player red;
  private Player white;

  @BeforeEach
  void setup() {
    red = new Player("Red");
    white = new Player("White");
    game = new Game(red,white);
    board = game.getBoard();
  }

  @Test
  void playerTest() {
    //set up
    assertTrue(game.currentPlayer().equals(red));
    game.switchPlayer();
    assertTrue(game.getCurrentPlayer().equals(white));
  }

  @Test
  void multipleJumpTest() {
    game.switchPlayer();
    //white
    board=new Checkerboard("multipleJumpWhite.txt");
    game.setBoard(board);
    Move move = new Move(new Position(4,3),new Position(2,5));
    move.setType(Move.Type.JUMP);
    game.makeMove(move);
    assertFalse(game.isValidTurn());
    move = new Move(new Position(2,5),new Position(0,3));
    move.setType(Move.Type.JUMP);
    game.makeMove(move);
    assertFalse(game.isValidTurn());
    move = new Move(new Position(0,3),new Position(2,1));
    move.setType(Move.Type.JUMP);
    game.makeMove(move);
    assertTrue(game.isValidTurn());


    game.switchPlayer();
    //red
    board=new Checkerboard("multipleJumpRed.txt");
    game.setBoard(board);
    move = new Move(new Position(3,4),new Position(5,2));
    move.setType(Move.Type.JUMP);
    game.makeMove(move);
    assertFalse(game.isValidTurn());
    move = new Move(new Position(5,2),new Position(7,4));
    move.setType(Move.Type.JUMP);
    game.makeMove(move);
    assertTrue(game.isValidTurn());
    move = new Move(new Position(7,4),new Position(6,3));
    move.setType(Move.Type.SINGLE);
    game.makeMove(move);
    assertFalse(game.isValidTurn());
  }

  @Test
  void SingleMoveTest() {
    board=new Checkerboard("jumpDiffColorRight.txt");
    game.setBoard(board);
    Move move = new Move(new Position(5,0),new Position(6,1));
    move.setType(Move.Type.SINGLE);
    game.makeMove(move);
    assertFalse(game.isValidTurn());

    game.switchPlayer();
    game.switchPlayer();

    board=new Checkerboard("jumpDiffColorRight.txt");
    game.setBoard(board);
    move = new Move(new Position(5,0),new Position(4,1));
    move.setType(Move.Type.SINGLE);
    game.makeMove(move);
    move = new Move(new Position(4,1),new Position(3,2));
    move.setType(Move.Type.SINGLE);
    game.makeMove(move);
    assertFalse(game.isValidTurn());
    game.switchPlayer();
    game.makeMove(new Move(new Position(2,3),new Position(3,2)));
    assertFalse(game.isValidTurn());
  }

  @Test
  void GameWonTest() {
    board=new Checkerboard("noMovesWhite.txt");
    game.setBoard(board);
    game.switchPlayer();
    game.makeMove(new Move(new Position(5,0),new Position(4,1)));
    game.isValidTurn();
    assertTrue(game.isGameWon());
    game = new Game(red,white);
    game.resigned();
    assertTrue(game.isGameWon());
    game = new Game(red,white);
    assertFalse(game.isGameWon());
  }


}
