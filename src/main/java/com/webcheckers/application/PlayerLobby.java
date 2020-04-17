package com.webcheckers.application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.webcheckers.model.Player;

/**
 * The object to hold the current players in the game.
 *
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    //
    // Constants
    //
    //
    // Attributes
    //

    private int totalPlayers = 0;
    private List<Player> players = new ArrayList<>();
    //
    // Constructors
    //

    //
    // Public methods
    //


    /**
     * Add a player to the lobby
     */
    public synchronized void addPlayer(Player player) {
        players.add(player);
        totalPlayers++;
    }

    /**
     * Remove a player from the lobby
     * @param player the player to remove
     */
    public synchronized void removePlayer(Player player){
        players.remove(player);
        totalPlayers--;
    }

    /**
     * Get the number of players signed in.
     *
     * @return
     *   The number of players signed in.
     */
    public synchronized int getTotalPlayers() {
        return totalPlayers;
    }

    /**
     * Get the list of names all players signed in.
     *
     * @return
     *   The string list names of players
     */
    public synchronized List<String> getPlayersNames() {
        List<String> playersNames = new ArrayList<>();
        for(Player player : players) {
            playersNames.add(player.getName());
        }
        return playersNames;
    }

    /**
     * Get the list of names all players signed in, except that send in the parameters
     *
     * @return
     *   The string list names of players, except the submitted one
     */
    public synchronized List<String> getPlayersNames(Player currentUser) {
        List<String> playersNames = new ArrayList<>();
        for(Player player : players) {
            if(!currentUser.equals(player))
                playersNames.add(player.getName());
        }
        return playersNames;
    }
    /**
     * Get the list of all players signed in.
     *
     * @return
     *   The list of players
     */
    public synchronized List<Player> getPlayersList() {
        return players;
    }

    /**
     * Check if a username is on the list already
     *
     * @return
     *      a boolean if the player is on the list, false if
     */
    public synchronized boolean lobbyContains(Player player) {
        if(player==null)
            return false;
        return players.contains(player);
    }
}
