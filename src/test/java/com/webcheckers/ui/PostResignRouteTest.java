package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Post Resign route unit tests
 * @author Chris Tremblay
 */
@Tag("ui")
public class PostResignRouteTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private PostResignRoute CuT;
    private GameCenter gameCenter;
    private GsonTester tester;
    private Player player;

    /**
     * set up envirmoent
     */
    @BeforeEach
    public void setup(){
        this.gson = new Gson();
        this.session = mock(Session.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.game = mock(Game.class);
        this.gameCenter = mock(GameCenter.class);
        this.playerServices = mock(PlayerServices.class);
        this.player = mock(Player.class);
        CuT = new PostResignRoute(gameCenter, gson);
    }

    /**
     * Makes sure json contains right stuff
     */
    @Test
    public void CheckResigned(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        doNothing().when(gameCenter).gameFinished(any());

        Object json = CuT.handle(request, response);
        assertTrue(((String)json).contains("Resigned"));
    }

    /**
     * Make sure that player is not currently playing a game
     */
    @Test
    public void playerCurrentlyPlaying(){
        when(gameCenter.isCurrentlyPlaying(any())).thenReturn(true);
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        doNothing().when(gameCenter).gameFinished(any());

        Object json = CuT.handle(request, response);
        assertTrue(((String)json).contains("Resigned"));
    }
}
