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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * unit tests for PostBackUpMoveRoute
 * @author Chris Tremblay
 */
@Tag("ui")
public class PostBackUpMoveTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private PostBackUpMoveRoute CuT;
    private GsonTester tester;

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
        CuT = new PostBackUpMoveRoute(gson);
    }

    /**
     * Test that json runs and does stuff
     */
    @Test
    public void backUpMove(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("undone"));
    }
}
