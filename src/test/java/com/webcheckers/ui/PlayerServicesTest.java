package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test suite for the {@Link PlayerServices} class
 * @author Chris Tremblay
 */
@Tag("Application-tier")
public class PlayerServicesTest {
    /** the object being tested */
    private PlayerServices CuT;

    /** A Mock object for game Center */
    private GameCenter gameCenter;

    /** Player userID */
    private final String uid = "player123";

    /** An opponent userID */
    private final String oppID = "player456";

    /**
     * Initial set up
     */
    @BeforeEach
    public void setup(){
        gameCenter = new GameCenter();
        CuT = new PlayerServices(gameCenter);
    }

    /**
     * Check set opponent
     */
    @Test
    public void setOpponent() {
        CuT.setOpponent(oppID);
        Player opp = CuT.getOpponent();
        assertEquals(oppID, opp.getName(), "Set opponent works");
    }

    /**
     * Check sign in status when not signed in
     */
    @Test
    public void isNotSignedIn(){
        // there is no userID associated with CuT yet
        assertFalse(CuT.isSignedIn());
    }

    /**
     * check sign in status when signed in
     */
    @Test
    public void isSignedIn(){
        // test sign in and make sure signed in
        CuT.signIn(uid);
        Player p = CuT.getThisPlayer();
        assertNotNull(p);
        assertTrue(CuT.isSignedIn());
    }

    /**
     * Check that when signing in that the player
     * get stored properly
     */
    @Test
    public void getThisPlayer(){
        CuT.signIn(uid);
        assertTrue(CuT.isSignedIn());
        Player p = CuT.getThisPlayer();
        assertNotNull(p);
        assertEquals(CuT.getThisPlayer().getName(), uid);
    }

    /**
     * Check if there is no opponent registered with
     * player services that you can't get a game from GameCenter
     */
    @Test
    public void currentGameNoOpponent(){
        // sign in
        CuT.signIn(uid);
        Player p = CuT.getThisPlayer();

        // Check no games associated with player
        Game gameCenterGame = gameCenter.findActiveGame(p);
        assertNull(gameCenterGame);

        // Get a new game
        Game playerGame = CuT.currentGame();
        assertNull(playerGame);

        // Make sure there isn't a game in GameCenter
        gameCenterGame = gameCenter.findActiveGame(p);
        assertNull(gameCenterGame);
    }

    /**
     * Check that when you have an opponent that you can
     * get a game from GameCenter when it does not exist yet
     */
    @Test
    public void currentGameNoGameExistsYet(){
        // sign in
        CuT.signIn(uid);
        Player p = CuT.getThisPlayer();

        // Check no games associated with player
        Game gameCenterGame = gameCenter.findActiveGame(p);
        assertNull(gameCenterGame);

        // Give player an opponent
        CuT.setOpponent(oppID);

        // If the opponent is not signed into GameCenter it will not
        // a game start. Assuming that the opponent you want is signed in
        gameCenter.signIn(CuT.getOpponent());

        // Get a new game
        Game playerGame = CuT.currentGame();
        assertNotNull(playerGame);

        // Make sure there isn't a game in GameCenter
        gameCenterGame = gameCenter.findActiveGame(p);
        assertNotNull(gameCenterGame);
        assertSame(gameCenterGame, playerGame);
    }

    /**
     * Once you have an opponent and have started a game
     * get the current game associated with that player
     */
    @Test
    public void currentGameGameExists(){
        // sign in
        CuT.signIn(uid);
        Player p = CuT.getThisPlayer();

        // Give player an opponent
        CuT.setOpponent(oppID);

        // If the opponent is not signed into GameCenter it will not
        // a game start. Assuming that the opponent you want is signed in
        gameCenter.signIn(CuT.getOpponent());

        // Query that there are no games associated with either player
        Game gameCenterGame = gameCenter.findActiveGame(p);
        assertNull(gameCenterGame);
        gameCenterGame = gameCenter.findActiveGame(CuT.getOpponent());
        assertNull(gameCenterGame);

        // create a game
        Game playerGame = CuT.currentGame();
        assertNotNull(playerGame);

        // get a game from the player and opponent
        playerGame = CuT.currentGame();
        assertNotNull(playerGame);
        Player opp = CuT.getOpponent();
        playerGame = gameCenter.findActiveGame(opp);
        assertNotNull(opp);
    }

    @Test
    public void finishedGame(){
        Player opp = new Player(oppID);
        gameCenter.signIn(opp);
        CuT.signIn(uid);
        CuT.setOpponent(oppID);
        CuT.currentGame();
        CuT.finishedGame();

        assertNull(CuT.getOpponent());
    }

    @Test
    public void getAGameButNotSignedIn(){
        Player opp = new Player(oppID);
        CuT.currentGame();

        assertNull(CuT.currentGame());
    }
}
