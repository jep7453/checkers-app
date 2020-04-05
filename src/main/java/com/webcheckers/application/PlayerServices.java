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
    private Player opponent;

    /** This player */
    private Player thisPlayer;

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
        this.thisPlayer= null;

    }

    /**
     * Create the current players name
     * @return the current players name as a string
     */
    public Player getThisPlayer() {
        return thisPlayer;
    }

    /**
     * Getter for the opponent
     * @return the opponent
     */
    public Player getOpponent(){
        return (opponent);
    }

    /**
     * Attempt to sign in player, if they are already signed in
     * it will not proceed
     * @param name name to sign in with
     * @return true if it could sign in, false if not
     */
    public boolean signIn( String name ){
        Player temp = new Player(name);
        if( gameCenter.signIn(temp) ){
            thisPlayer = temp;
            return (true);
        }
        return (false);
    }

    /**
     * Sign out this player from the game center
     */
    public void signOut(){
        gameCenter.signOut(thisPlayer);
        thisPlayer = null;
        opponent = null;
        game = null;
    }

    /**
     * Check if the player is signed in or not
     * @return true if signed in to game center, false if not
     */
    public boolean isSignedIn(){
        if(thisPlayer == null)
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
        // let game center know player finished game
    }

    /**
     * set the opponent
     * @param opponent the opponent
     */
    public void setOpponent( String opponent ){
        this.opponent = new Player(opponent);
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
        if(this.game == null) {
            gameCenter.playerStartedPlayingGame(thisPlayer);
            gameCenter.playerStartedPlayingGame(opponent);
            // Let game center know player is starting a new game and create a new game
            Game temp = gameCenter.findActiveGame(thisPlayer);
            if(temp == null)
                game = gameCenter.getGame(thisPlayer, opponent);
            else
                game = temp;
        }
        return (game);
    }
}
