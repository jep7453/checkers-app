package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

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
    private ArrayList<Player> currentlyPlaying;

    /** Player lobby */
    private PlayerLobby lobby;

    /** list of all games in game center */
    private ArrayList<Game> games;

    /**
     * Create a new GameCenter object
     */
    public GameCenter(){
        currentlyPlaying = new ArrayList<>();
        lobby = new PlayerLobby();
        games = new ArrayList<>();
    }

    /**
     * Add a player to the list of players signed in
     *
     * @param player the player to check for
     * @return true if signed in, false if not
     */
    public synchronized boolean isSignedIn(Player player){
        if(player==null)
            return false;
        return (lobby.lobbyContains(player));
    }

    /**
     * Sign in a player
     *
     * @param player the name of player
     * @return true if it could sign in, false if not
     */
    public synchronized boolean signIn(Player player){
        if( isSignedIn(player) ){
            LOG.fine("Attempted sign with name: + '" + player.getName() + "', that name already taken");
            return (false);
        }
        lobby.addPlayer(player);
        LOG.fine("Player '" + player.getName() + "' signed in");
        return (true);
    }

    /**
     * Checks to see if player is signed in, if so
     * removes players user name from list
     *
     * @param player user name to sign out
     */
    public synchronized void signOut( Player player  ) {
        // remove from game list incase
        if(isCurrentlyPlaying(player)){
            playerFinishedPlayingGame(player);
        }

        // remove from lobby
        lobby.removePlayer(player);
    }

    /**
     * Allow GameCenter to know that a player started playing
     * @param player player playing game
     */
    public synchronized void playerStartedPlayingGame( Player player ){
                currentlyPlaying.add(player);
                LOG.fine("Player '" + player.getName() + "' started player a game");
            }


    /**
     * Allow GameCenter to know that a player is not currently playing a game
     * @param player the name of player who finished a game
     */
    public synchronized void playerFinishedPlayingGame( Player player ){
        currentlyPlaying.remove(player);
        currentlyPlaying.remove(player);
        LOG.fine("Player '" + player + "' finished playing a game");
    }

    /**
     * Tells game center that player isn't player and removes game
     * @param game the game to remove
     */
    public void gameFinished(Game game){
        if(games.contains(game)){
            playerFinishedPlayingGame(game.getRedPlayer());
            playerFinishedPlayingGame(game.getWhitePlayer());
            games.remove(game);

        }
    }

    /**
     * Check to see if a player is currently playing a game or not
     *
     * @param player player to check for
     * @return true if player is currently playing a game
     */
    public boolean isCurrentlyPlaying( Player player ){
        return currentlyPlaying.contains(player);
    }

    /**
     * Get a new game between players
     *
     * @param player1 first player
     * @param player2 second player
     * @return a new game object
     */
    public Game getGame( Player player1, Player player2 ) {
        // create game iff both players are signed in
        if(isSignedIn(player1) && isSignedIn(player2)){
            Game game = new Game(player1, player2);
            games.add(game);
            LOG.fine("Creating new game for player '" + player1.getName() +
                    "' and '" + player2.getName() + "'");
            playerStartedPlayingGame(player1);
            playerStartedPlayingGame(player2);
            return (game);
        }

        // both players not signed in
        LOG.fine("Could not create game for player '" + player1.getName() +
                "' and '\" + player2.getName()");
        return null;
    }

    public Game findActiveGame(Player player){
        // search list of games
        for(Game g : games){
            Player gRedPlayer = g.getRedPlayer();
            Player gWhitePlayer = g.getWhitePlayer();
            if(player.getName().equals(gRedPlayer.getName()) ||
            player.getName().equals(gWhitePlayer.getName())){
                return (g);
            }
        }
        return (null);
    }

    /**
     * Get the game lobby
     * @return the game lobby
     */
    public PlayerLobby getLobby() {
        return lobby;
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