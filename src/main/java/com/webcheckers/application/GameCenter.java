package com.webcheckers.application;

import java.util.ArrayList;

/**
 * This class is used to keep track of all global game information
 *
 * @author Chris Tremblay
 */
public class GameCenter {

    /** List of all users currently signed in */
    private ArrayList<String> playersSignedIn;

    /**
     * Create a new GameCenter object
     */
    public GameCenter(){
        playersSignedIn = new ArrayList<>();
    }

    /**
     * Checks to see if a players user name is currently in user
     *
     * @param userID the user id to check for
     * @return true if signed in, false if not
     */
    public synchronized boolean isSignedIn( String userID ){
        return( playersSignedIn.contains(userID ) );
    }

    /**
     * Adds a player to the signed in list
     *
     * @param userID username to add
     */
    public synchronized void signIn( String userID ){
        playersSignedIn.add(userID);
    }

    /**
     * Checks to see if player is signed in, if so
     * removes players user name from list
     *
     * @param userID user name to sign out
     */
    public synchronized void signOut(String userID){
        if( isSignedIn(userID ) ){
            playersSignedIn.remove(userID);
        }
    }

}