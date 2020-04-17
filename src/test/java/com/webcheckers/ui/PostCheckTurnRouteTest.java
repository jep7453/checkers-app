package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostCheckTurnRouteTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private PostCheckTurnRoute CuT;
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
        this.playerServices = mock(PlayerServices.class);
        this.player = mock(Player.class);
        CuT = new PostCheckTurnRoute(gson);
    }

    /**
     * Test turn is valid
     */
    @Test
    public void turnIsTrue(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        when(game.currentPlayer()).thenReturn(player);
        when(playerServices.currentGame()).thenReturn(game);
        when(playerServices.getThisPlayer()).thenReturn(player);
       //when(player.equals(player)).thenReturn(true);

        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("true"));
    }

    /**
     * test turn is invalid
     */
    @Test
    public void turnIsFalse(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        when(game.currentPlayer()).thenReturn(player);
        Player test = new Player("test");
        when(playerServices.getThisPlayer()).thenReturn(test);
        //when(player.equals(test)).thenReturn(false);

        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("Waiting for turn"));
    }
}
