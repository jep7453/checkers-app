package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

public class PlayerServices {

    /** The global game center */
    private GameCenter gameCenter;

    /** The players current game */
    private Game game;

    private Player opponent;

    private Player thisPlayer;

    private boolean isSignedIn;

    /**
     * Create a new PlayerServices
     * @param gameCenter the game center for the whole game
     */
    public PlayerServices( GameCenter gameCenter ){
        this.gameCenter = gameCenter;
        this.game = null;
        this.isSignedIn = false;
    }

    /**
     * Set game to null to signify that a player has finished a gam
     */
    public void finishedGame(){
        this.game = null;
        this.opponent = null;
    }

    /**
     * set the opponent
     * @param opponent the opponent
     */
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     * Get the current game or a new game, or nothing if there is no opponent
     * @return a new game, or null if there is not an opponent stored
     */
    public Game currentGame(){
        if(this.opponent == null)
            return (null);
        if(this.game == null)
            return (gameCenter.getGame( thisPlayer, opponent));
        return (game);
    }
}
