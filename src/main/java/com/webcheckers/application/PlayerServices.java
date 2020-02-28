package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

public class PlayerServices {

    /** The global game center */
    private GameCenter gameCenter;

    /** The players current game */
    private Game game;

    /** The opponent the player is playing against */
    private String opponent;

    /** This player */
    private String thisPlayer;

    /** Is this player signed in? */
    private boolean signedIn;

    /**
     * Create a new PlayerServices
     * @param gameCenter the game center for the whole game
     */
    public PlayerServices( GameCenter gameCenter ){
        this.gameCenter = gameCenter;
        this.game = null;
        this.signedIn = false;
    }

    /**
     * Attempt to sign in player, if they are already signed in
     * it will not proceed
     * @param name name to sign in with
     * @return true if it could sign in, false if not
     */
    public boolean signIn( String name ){
        if( gameCenter.isSignedIn(name)) {
            return (false);
        }
        gameCenter.signIn(thisPlayer);
        signedIn = true;
        return (true);
    }

    /**
     * Sign out this player from the game center
     */
    public void signOut(){
        gameCenter.signOut(thisPlayer);
        signedIn = false;
    }

    /**
     * Check if the player is signed in or not
     * @return true if signed in to game center, false if not
     */
    public boolean isSignedIn(){
        return (signedIn);
    }

    /**
     * Set game to null to signify that a player has finished a game.
     * Also set opponent to null
     */
    public void finishedGame(){
        this.game = null;
        this.opponent = null;
    }

    /**
     * set the opponent
     * @param opponent the opponent
     */
    public void setOpponent(String opponent){
        this.opponent = opponent;
    }

    /**
     * Get the current game or a new game, or nothing if there is no opponent
     * @return a new game, or null if there is not an opponent stored or not signed in
     */
    public Game currentGame(){
        if( !isSignedIn() )
            return (null);
        if(this.opponent == null)
            return (null);
        if(this.game == null)
            return ( gameCenter.getGame( new Player(thisPlayer), new Player(opponent) ) );
        return (game);
    }
}
