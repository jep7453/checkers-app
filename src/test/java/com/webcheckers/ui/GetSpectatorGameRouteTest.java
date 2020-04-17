package com.webcheckers.ui;

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

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Test suite for GetSpectatorGameRoute
 * @author Chris Tremblay
 */
@Tag("ui")
public class GetSpectatorGameRouteTest {
    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private Game game;
    private GetSpectatorGameRoute CuT;
    private GameCenter gameCenter;
    private GsonTester tester;
    private Player redplayer;
    private Player whitePlayer;
    private Checkerboard board;
    private Move move;
    private TemplateEngineTester testHelper;
    private TemplateEngine engine;
    private String gameID = "1234";
    private Player spectator;

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
        this.spectator = mock(Player.class);
        CuT = new GetSpectatorGameRoute(gameCenter, engine);

        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(request.queryParams(GetGameRoute.GAME_ID)).thenReturn(gameID);
        when(request.queryParams("player")).thenReturn("opponent");

        when(redplayer.getName()).thenReturn("red");
        when(whitePlayer.getName()).thenReturn("white");
        when(spectator.getName()).thenReturn("spectator");
    }

    /**
     * the game is there
     */
    @Test
    public void gameNotNull(){
        when(playerServices.getThisPlayer()).thenReturn(spectator);
        Game testGame = new Game( redplayer, whitePlayer);
        testGame.startedSpectating(new Player("player"));
        when(gameCenter.gameFromID(anyString())).thenReturn(testGame);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        try {
            CuT.handle(request, response);
        } catch (Exception e){
            // shouldn't go here
            fail();
        }

        verify(response, times(0)).redirect(any(String.class));
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, redplayer.getName());
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, whitePlayer.getName());
        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_USER, whitePlayer.getName());
    }

    /**
     * if the game doesn't exist for some reason
     */
    @Test
    public void gameNull(){
        when(playerServices.getThisPlayer()).thenReturn(spectator);
        when(gameCenter.gameFromID(any(String.class))).thenReturn(null);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        try {
            CuT.handle(request, response);
        } catch (Exception e){

        }

        verify(response, times(1)).redirect(any(String.class));
    }
}
