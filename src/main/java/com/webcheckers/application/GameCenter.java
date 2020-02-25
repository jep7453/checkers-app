package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

/**
 * This class is used to keep track of all global game information
 *
 * @author Chris Tremblay
 */
public class GameCenter {

    /** List of all users currently signed in */
    private ArrayList<String> playersSignedIn;

    /** Players playing a game */
    private ArrayList<String> currentlyPlaying;

    /**
     * Create a new GameCenter object
     */
    public GameCenter(){
        playersSignedIn = new ArrayList<>();
        currentlyPlaying = new ArrayList<>();
    }

    /**
     * Checks to see if a player is signed in
     *
     * @param player the user id to check for
     * @return true if signed in, false if not
     */
    public synchronized boolean isSignedIn( String player ){
        return( playersSignedIn.contains(player ) );
    }

    /**
     * Adds a player to the signed in list
     *
     * @param player username to add
     */
    public synchronized void signIn( String player ){
        playersSignedIn.add(player);
    }

    /**
     * Checks to see if player is signed in, if so
     * removes players user name from list
     *
     * @param player user name to sign out
     */
    public synchronized void signOut( String player  ) {
        if (isSignedIn(player)) {
            playersSignedIn.remove(player);
        }
    }

    /**
     * Allow GameCenter to know that a player started playing
     * @param name player playing game
     */
    public synchronized void playerStartedPlayingGame( String name ){
        currentlyPlaying.add(name);
    }

    /**
     * Allow GameCenter to know that a player is not currently playing a game
     * @param name the name of player who finished a game
     */
    public synchronized void playerFinishedPlayingGame( String name ){
        currentlyPlaying.remove(name);
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
        return new Game(player1, player2);
    }

    /**
     * Get the list of players currently signed in
     * @return the ArrayList of players currently signed in
     */
    public synchronized ArrayList<String> getPlayers() {
        return playersSignedIn;
    }
}