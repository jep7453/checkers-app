package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for PostSubmitTurn
 * @author Chris Tremblay
 */
@Tag("ui")
public class PostSubmitTurnTest {

    /** The component under test */
    PostSubmitTurnRoute CuT;

    /** The request */
    private Request request;

    private Response response;
    private TemplateEngine engine;
    private PlayerServices playerServices;
    private Session session;
    private Game game;
    private Gson gson;

    /**
     * Set up enviro
     */
    @BeforeEach
    public void setup(){
        this.session = mock(Session.class);
        this.request = mock(Request.class);
        this.session = mock(Session.class);
        this.game = mock(Game.class);
        this.playerServices = mock(PlayerServices.class);
        gson = new Gson();
        CuT = new PostSubmitTurnRoute(gson);

        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
    }

    /**
     * test a valid move
     */
    @Test
    public void moveIsValid(){
        when(game.isValidTurn()).thenReturn(true);
        CuT.handle(request, response);
    }

    /**
     * Test a valid move
     */
    @Test
    public void moveIsntValid(){
        when(game.isValidTurn()).thenReturn(false);
        CuT.handle(request, response);
    }
}
