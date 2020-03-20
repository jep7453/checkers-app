package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author Bryan Basham
 */
@Tag("Application-tier")
public class PlayerLobbyTest {


  private final Player player1 =new Player("Player 1");
  private final Player player2 =new Player("Player 2");
  private List<String> players = new ArrayList<>();

  /**
   * Test the ability to add a player.
   */
  @Test
  public void addPlayerTest() {
    final PlayerLobby CuT = new PlayerLobby();
    // Invoke test
    CuT.addPlayer(player1);
    players.add("Player 1");
    // Analyze results
    assertNotNull(CuT.getPlayersNames());
    assertEquals(CuT.getPlayersNames(),players);
  }

  /**
   * Test the ability to count number of plays
   */
  @Test
  public void totalPlayersTest() {
    final PlayerLobby CuT = new PlayerLobby();
    // Invoke test
    CuT.addPlayer(player1);
    int one = CuT.getTotalPlayers();
    CuT.addPlayer(player2);
    int two = CuT.getTotalPlayers();

    // Analyze the results
    assertEquals(one,1);
    assertEquals(two,2);
  }

  /**
   * Test the ability to check if the lobby contains a player
   */
  @Test
  public void containsPlayerTest() {
    final PlayerLobby CuT = new PlayerLobby();
    // Invoke test
    CuT.addPlayer(player1);

    // Analyze the results
    assertTrue(CuT.lobbyContains(player1));
    assertFalse(CuT.lobbyContains(player2));
  }

  /**
   * Test the ability to return the players, excluding one in parameters
   */
  @Test
  public void getPlayersTest() {
    final PlayerLobby CuT = new PlayerLobby();
    // Invoke test
    CuT.addPlayer(player1);
    CuT.addPlayer(player2);
    players.add("Player 2");

    // Analyze the results
    assertEquals(CuT.getPlayersNames(player1),players);
  }

}