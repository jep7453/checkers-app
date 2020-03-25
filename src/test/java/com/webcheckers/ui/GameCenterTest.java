package com.webcheckers.ui;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;

/**
 * This is a test suite for the @Link GameCenterTest class
 * @author Sree Jupudy
 */
@Tag("Application")
public class GameCenterTest {
    private final Player player1 =new Player("Player 1");
    private final Player player2 =new Player("Player 2");
    private List<Player> CurPlaying = new ArrayList<>();

    @Test
    public void isSignedInTest(){
        //Game game = new Game(player1, player2);
        final GameCenter CuT = new GameCenter();
        assertEquals(CuT.isSignedIn(player1),false);

        PlayerLobby lobby = new PlayerLobby();
        lobby.addPlayer(player1);
        assertEquals(CuT.isSignedIn(player1),false);
    }

    @Test
    public void signInTest(){
        //Game game = new Game(player1, player2);
        final GameCenter CuT = new GameCenter();
        assertEquals(CuT.signIn(player1),true);

        CuT.signIn(player2);
        assertEquals(CuT.signIn(player2),false);
    }

    public void playerStartedPlayingGameTest(){
        final GameCenter CuT = new GameCenter();
    }

    @Test
    public void isCurrentlyPlaying(){
        final GameCenter CuT = new GameCenter();
        CuT.playerStartedPlayingGame(player2);
        assertEquals(CuT.isCurrentlyPlaying(player2),true);
        assertEquals(CuT.isCurrentlyPlaying(player1),false);
    }
}