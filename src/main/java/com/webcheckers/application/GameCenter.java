package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class is used to keep track of all global game information
 *
 * @author Chris Tremblay
 */
public class GameCenter {
    /** Logger for the server */
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    /** Players playing a game */
    private ArrayList<String> currentlyPlaying;

    /** Player lobby */
    private PlayerLobby lobby;

    /**
     * Create a new GameCenter object
     */
    public GameCenter(){
        currentlyPlaying = new ArrayList<>();
        lobby = new PlayerLobby();
    }

    /**
     * Add a player to the list of players signed in
     *
     * @param player the player to check for
     * @return true if signed in, false if not
     */
    public synchronized boolean isSignedIn(String player){
        return (lobby.lobbyContains(new Player(player)));
    }

    /**
     * Sign in a player
     *
     * @param name the name of player
     * @return true if it could sign in, false if not
     */
    public synchronized boolean signIn(String name){
        if( isSignedIn(name) ){
            LOG.fine("Attempted sign with name: + '" + name + "', that name already taken");
            return (false);
        }
        lobby.addPlayer(new Player(name));
        LOG.fine("Player '" + name + "' signed in");
        return (true);
    }

    /**
     * Checks to see if player is signed in, if so
     * removes players user name from list
     *
     * @param player user name to sign out
     */
    public synchronized void signOut( String player  ) {
        // todo - eventually
    }

    /**
     * Allow GameCenter to know that a player started playing
     * @param name player playing game
     */
    public synchronized void playerStartedPlayingGame( String name ){
        currentlyPlaying.add(name);
        LOG.fine("Player '" + name + "' started player a game" );
    }

    /**
     * Allow GameCenter to know that a player is not currently playing a game
     * @param name the name of player who finished a game
     */
    public synchronized void playerFinishedPlayingGame( String name ){
        currentlyPlaying.remove(name);
        LOG.fine("Player '" + name + "' finished playing a game");
    }

    /**
     * Check to see if a player is currently playing a game or not
     *
     * @param player player to check for
     * @return true if player is currently playing a game
     */
    public boolean isCurrentlyPlaying( String player ){
        return currentlyPlaying.contains(player);
    }

    /**
     * Get a new game between players
     *
     * @param player1 first player
     * @param player2 second player
     * @return a new game object
     */
    public Game getGame( Player player1, Player player2 ){
        LOG.fine("Creating new game for player '" + player1.getName() +
                "' and '" + player2.getName() + "'");
        return new Game(player1, player2);
    }

    /**
     * Get a new player services object from game center
     * @return a new player services object
     */
    public PlayerServices newPlayerServices(){
        LOG.fine("Creating new PlayerServices Object");
        return (new PlayerServices(this));
    }
}