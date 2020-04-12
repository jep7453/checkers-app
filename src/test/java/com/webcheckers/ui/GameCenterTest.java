package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a test suite for the @Link GameCenterTest class
 * @author Sree Jupudy
 */
@Tag("Application")
public class GameCenterTest {
    private Player player1;
    private Player player2;
    private List<Player> CurPlaying = new ArrayList<>();
    private GameCenter CuT;

    @BeforeEach
    public void setup(){
        CuT = new GameCenter();
        player1 = new Player("player1");
        player2 = new Player("player2");
    }

    @Test
    public void isSignedInTest(){
        //Game game = new Game(player1, player2);
        assertEquals(CuT.isSignedIn(player1),false);

        PlayerLobby lobby = new PlayerLobby();
        lobby.addPlayer(player1);
        assertEquals(CuT.isSignedIn(player1),false);
    }

    @Test
    public void signInTest(){
        //Game game = new Game(player1, player2);
        assertEquals(CuT.signIn(player1),true);

        CuT.signIn(player2);
        assertEquals(CuT.signIn(player2),false);
    }

    public void playerStartedPlayingGameTest(){
        final GameCenter CuT = new GameCenter();
    }

    @Test
    public void isCurrentlyPlaying(){
        CuT.playerStartedPlayingGame(player2);
        assertEquals(CuT.isCurrentlyPlaying(player2),true);
        assertEquals(CuT.isCurrentlyPlaying(player1),false);
    }

    /**
     * Test that this funcctino works
     */
    @Test
    public void playerFinishedGame(){
        CuT.playerStartedPlayingGame(player1);
        assertTrue(CuT.isCurrentlyPlaying(player1));
        CuT.playerFinishedPlayingGame(player1);
        assertFalse(CuT.isCurrentlyPlaying(player1));
    }

    /**
     * make sure game finished removes game from list
     */
    @Test
    public void gameFinished(){
        CuT.signIn(player1);
        CuT.signIn(player2);
        Game game = mock(Game.class);
        when(game.isGameWon()).thenReturn(true);
        when(game.getRedPlayer()).thenReturn(player1);
        when(game.getWhitePlayer()).thenReturn(player1);

        CuT.gameFinished(game);
        assertNull(CuT.findActiveGame(player1));
    }

    /**
     * make sure game finished removes game from list
     */
    @Test
    public void getGameNoSignIn(){
        CuT.signIn(player1);
        Game game = CuT.getGame(player1, player2);
        assertNull(CuT.findActiveGame(player1));
    }

    /**
     * make sure sign out logic works
     */
    @Test
    public void signOut(){
        CuT.signIn(player1);
        assertTrue(CuT.isSignedIn(player1));
        assertFalse(CuT.isSignedIn(player2));
        CuT.signOut(player1);
        assertFalse(CuT.isSignedIn(player1));
    }

    /**
     * New player services returns a new players services
     */
    @Test
    public void playerServicesGet(){
        PlayerServices test =  CuT.newPlayerServices();
        assertSame(test.getClass(), PlayerServices.class);
    }
}