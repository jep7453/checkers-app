package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;

import java.util.ArrayList;
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

    private boolean gameOver;

    private boolean canLeaveGame;

    /** list of a players replays */
    private ArrayList<Replay> replays;

    /**
     * Create a new PlayerServices
     * @param gameCenter the game center for the whole game
     */
    public PlayerServices( GameCenter gameCenter ){
        this.gameCenter = gameCenter;
        this.game = null;
        this.signedIn = false;
        this.thisPlayer= null;
        this.gameOver=false;
        this.canLeaveGame=false;
        replays = new ArrayList<>();

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

    public void setGameOver(boolean over) {
        gameOver=over;
        if(gameOver==true) {
            gameCenter.gameFinished(game);
        }
    }

    /**
     * Get the game ID from the game
     * @return the game ID
     */
    public String getGameID(){
        if(game==null)
            return null;
        return (game.getGameID());
    }

    /**
     * Is the game over
     * @return
     */
    public boolean isGameOver() {
        return gameOver;
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
        this.thisPlayer.setIsPlaying(false);

        canLeaveGame=false;
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

            // Let game center know player is starting a new game and create a new game
            Game temp = gameCenter.findActiveGame(thisPlayer);
            if(temp == null) {
                if(!gameCenter.isCurrentlyPlaying(thisPlayer)) {
                    gameCenter.playerStartedPlayingGame(thisPlayer);
                }
                if(!gameCenter.isCurrentlyPlaying(opponent)) {
                    gameCenter.playerStartedPlayingGame(opponent);
                }
                game = gameCenter.getGame(thisPlayer, opponent);
            }
            else
                game = temp;
        }
        canLeaveGame=true;
        this.thisPlayer.setIsPlaying(true);
        return (game);
    }

    /**
     * Can the player leave the game?
     * @return true if they can, false if not
     */
    public boolean getCanLeaveGame() {
        return canLeaveGame;
    }

    /**
     * Get a replay from the ID
     * @param gameID the game id
     */
    public Replay replayFromID(String gameID) {
        for(Replay r : replays){
            if(r.getGameID().equals(gameID))
                return (r);
        }
        return (null);
    }

    /**
     * Put a game the player just played into their replay list
     * @param replay the replay to save
     */
    public void addReplay(Replay replay) {
        replays.add(replay);
    }
}
