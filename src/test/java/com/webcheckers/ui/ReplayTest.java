package com.webcheckers.ui;


import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReplayTest {

    private Game game;
    private Replay replay;
    private final String RED_PLAYER = "red";
    private final String WHITE_PLAYER = "white";


    @Test
    void hasNextPrevMoves() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));

        //setup
        game.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        game.switchPlayer();
        game.makeMove(new Move(new Position(5, 6), new Position(4, 7)));
        game.switchPlayer();
        replay = new Replay(game);

        assertTrue(replay.hasNextMove());
        replay.makeNextTurn();
        assertTrue(replay.hasNextMove());
        replay.makeNextTurn();
        assertFalse(replay.hasNextMove());

        assertTrue(replay.hasPrevMove());
        replay.makePrevTurn();
        assertTrue(replay.hasPrevMove());
        replay.makePrevTurn();
        assertFalse(replay.hasPrevMove());
    }

    @Test
    void getTitle() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));
        replay = new Replay(game);
        assertTrue(replay.getTitle().equals(game.getTitle()));
    }

    @Test
    void makeNextPrevTurn() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));

        //setup
        game.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        game.switchPlayer();
        game.makeMove(new Move(new Position(5, 6), new Position(4, 7)));
        game.switchPlayer();
        replay = new Replay(game);


        assertTrue(replay.makeNextTurn());
        assertTrue(replay.getMovesMade() == 1);
        assertTrue(replay.makeNextTurn());
        assertTrue(replay.getMovesMade() == 2);
        assertFalse(replay.makeNextTurn());

        assertTrue(replay.makePrevTurn());
        assertTrue(replay.getMovesMade() == 1);
        assertTrue(replay.makePrevTurn());
        assertTrue(replay.getMovesMade() == 0);
        assertFalse(replay.makePrevTurn());
    }


    @Test
    void getRedWhitePlayer() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));
        replay = new Replay(game);
        assertTrue(replay.getRedPlayer().equals(game.getRedPlayer()));
        assertTrue(replay.getWhitePlayer().equals(game.getWhitePlayer()));
    }

    @Test
    void isRedPlayerTurn() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));

        //setup
        game.makeMove(new Move(new Position(2, 3), new Position(3, 2)));
        game.switchPlayer();
        game.makeMove(new Move(new Position(5, 6), new Position(4, 7)));
        game.switchPlayer();
        replay = new Replay(game);

        replay = new Replay(game);
        assertTrue(replay.isRedPlayerTurn());
        replay.makeNextTurn();
        replay.updatePlayer();
        assertTrue(!replay.isRedPlayerTurn());
    }


    @Test
    void getGameID() {
        game = new Game(new Player(RED_PLAYER), new Player(WHITE_PLAYER));
        replay = new Replay(game);
        assertTrue(replay.getGameID().equals(game.getGameID()));
    }




}
