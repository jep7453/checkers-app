package com.webcheckers.ui;

/***
 * This is unit test for BoardView class of UI Tier.
 * @author Kesa Abbas Lnu <kl3468rit.edu>
 */

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Checkerboard;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Unit tests for get game route
 * @author Chris Tremblay
 */
@Tag("ui")
public class GetGameRouteTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private GetGameRoute CuT;
    private GameCenter gameCenter;
    private GsonTester tester;
    private Player redplayer;
    private Player whitePlayer;
    private Checkerboard board;
    private Move move;
    private TemplateEngineTester testHelper;
    private TemplateEngine engine;
    private String gameID = "1234";

    /**
     * set up envirmoent
     */
    @BeforeEach
    public void setup(){
        this.gson = new Gson();
        this.engine = mock(TemplateEngine.class);
        this.testHelper = new TemplateEngineTester();
        this.session = mock(Session.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.game = mock(Game.class);
        this.gameCenter = mock(GameCenter.class);
        this.playerServices = mock(PlayerServices.class);
        this.redplayer = mock(Player.class);
        this.whitePlayer = mock(Player.class);
        this.move = mock(Move.class);
        this.board = new Checkerboard();
        CuT = new GetGameRoute(engine, gameCenter);

        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(request.queryParams(GetGameRoute.GAME_ID)).thenReturn(gameID);
        when(request.queryParams("player")).thenReturn("opponent");

        when(redplayer.getName()).thenReturn("red");
        when(whitePlayer.getName()).thenReturn("white");
    }

    /**
     * Test that game is over
     */
    @Test
    public void playerResigned(){
        when(playerServices.getThisPlayer()).thenReturn(whitePlayer);
        when(playerServices.isGameOver()).thenReturn(true);
        try{
            CuT.handle(request, response);
            fail();
        } catch (HaltException e){
            verify(response, atLeastOnce()).redirect(anyString());
        }
    }

    @Test
    public void getGameAlreadyExistsWhiteFirst(){
        when(playerServices.getThisPlayer()).thenReturn(whitePlayer);
        when(playerServices.currentGame()).thenReturn(game);
        when(playerServices.getOpponent()).thenReturn(redplayer);

        when(gameCenter.isCurrentlyPlaying(any())).thenReturn(true);

        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getRedPlayer()).thenReturn(redplayer);
        when(game.getCurrentPlayer()).thenReturn(redplayer);
        when(game.getBoard()).thenReturn(board);

        when(engine.render(any())).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR,
                GetGameRoute.ActiveColor.RED);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, redplayer.getName());
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER,
                playerServices.getThisPlayer().getName());
    }

    /**
     * Test when game is already going and current user is red player
     */
    @Test
    public void getGameAlreadyExistsRedFirst(){
        when(playerServices.getThisPlayer()).thenReturn(redplayer);
        when(playerServices.currentGame()).thenReturn(game);
        when(playerServices.getOpponent()).thenReturn(whitePlayer);

        when(gameCenter.isCurrentlyPlaying(any())).thenReturn(true);

        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getRedPlayer()).thenReturn(redplayer);
        when(game.getCurrentPlayer()).thenReturn(whitePlayer);
        when(game.getBoard()).thenReturn(board);

        when(engine.render(any())).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR,
                GetGameRoute.ActiveColor.WHITE);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, whitePlayer.getName());
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER,
                playerServices.getThisPlayer().getName());
    }

    /**
     * Test when game hasn't been made yet
     */
    @Test
    public void gameDoesNotExist(){
        when(playerServices.getThisPlayer()).thenReturn(redplayer);
        when(playerServices.currentGame()).thenReturn(game);
        when(playerServices.getOpponent()).thenReturn(whitePlayer);

        when(gameCenter.isCurrentlyPlaying(any())).thenReturn(false);

        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getRedPlayer()).thenReturn(redplayer);
        when(game.getCurrentPlayer()).thenReturn(whitePlayer);
        when(game.getBoard()).thenReturn(board);

        when(engine.render(any())).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR,
                GetGameRoute.ActiveColor.WHITE);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, whitePlayer.getName());
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER,
                playerServices.getThisPlayer().getName());
    }

    @Test
    public void gameEnded(){when(playerServices.getThisPlayer()).thenReturn(redplayer);
        when(playerServices.currentGame()).thenReturn(game);
        when(playerServices.getOpponent()).thenReturn(whitePlayer);

        when(gameCenter.isCurrentlyPlaying(any())).thenReturn(false);

        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getRedPlayer()).thenReturn(redplayer);
        when(game.getCurrentPlayer()).thenReturn(whitePlayer);
        when(game.getBoard()).thenReturn(board);
        when(game.isGameWon()).thenReturn(true);

        when(engine.render(any())).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        Map<String,Object> modeOptionsAsJSON = new HashMap<>();
        modeOptionsAsJSON.put("isGameOver",true);
        testHelper.assertViewModelAttribute(GetGameRoute.MODE_OPTIONS_AS_JSON,gson.toJson(modeOptionsAsJSON) );
    }
}