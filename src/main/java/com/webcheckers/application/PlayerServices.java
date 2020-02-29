package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.logging.Logger;

public class PlayerServices {
    /** Logger for the server */
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

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
        thisPlayer = name;
        return gameCenter.signIn(name);
    }

    /**
     * Sign out this player from the game center
     */
    public void signOut(){
        /*gameCenter.signOut(thisPlayer);
        signedIn = false;*/
    }

    /**
     * Check if the player is signed in or not
     * @return true if signed in to game center, false if not
     */
    public boolean isSignedIn(){
        if(thisPlayer.equals(""))
            return (false);
        return (gameCenter.isSignedIn(thisPlayer));
    }

    /**
     * Set game to null to signify that a player has finished a game.
     * Also set opponent to null
     */
    public void finishedGame(){
        this.game = null;
        this.opponent = null;
        gameCenter.playerFinishedPlayingGame(new Player(thisPlayer));
    }

    /**
     * set the opponent
     * @param opponent the opponent
     */
    public void setOpponent( String opponent ){
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
        gameCenter.playerFinishedPlayingGame(new Player(thisPlayer));
        if(this.game == null) {
            game = gameCenter.getGame(new Player(thisPlayer), new Player(opponent));
        }
        return (game);
    }
}
