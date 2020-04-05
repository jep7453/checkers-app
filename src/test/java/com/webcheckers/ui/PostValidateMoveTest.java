package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * unit tests for psot validate move
 * @author Chris Tremblay
 */
@Tag("ui")
public class PostValidateMoveTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private PostValidateMoveRoute CuT;
    private GameCenter gameCenter;
    private GsonTester tester;
    private Player player;
    private Move move;

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
        this.move = mock(Move.class);
        CuT = new PostValidateMoveRoute(gson);
    }

    /**
     * Test to see if a move was moade
     */
    @Test
    public void moveMadeSingle(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        Move move = new Move(new Position(0,0), new Position(1,1));
        when(game.isValidMove(any(Move.class))).thenReturn(Move.Type.SINGLE);
        Gson toJson = new Gson();
        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));

        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("Move made"));
    }

    /**
     * Test to see if a move was moade
     */
    @Test
    public void moveMadeJump(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        Move move = new Move(new Position(0,0), new Position(1,1));
        when(game.isValidMove(any(Move.class))).thenReturn(Move.Type.JUMP);
        Gson toJson = new Gson();
        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));

        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("Move made"));
    }

    /**
     * Test to see if a move is invalid
     */
    @Test
    public void moveInvalid(){
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.currentGame()).thenReturn(game);
        Move move = new Move(new Position(0,0), new Position(1,1));
        when(game.isValidMove(any(Move.class))).thenReturn(Move.Type.INVALID);
        Gson toJson = new Gson();
        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));

        Object stuff = CuT.handle(request, response);
        assertTrue(((String)stuff).contains("Move Invalid"));
    }
}
