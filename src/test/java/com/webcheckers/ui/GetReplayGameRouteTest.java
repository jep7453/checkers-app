package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetReplayGameRouteTest {

    private Gson gson;
    private Session session;
    private PlayerServices playerServices;
    private Request request;
    private Response response;
    private GetReplayGameRoute CuT;
    private GameCenter gameCenter;
    private Checkerboard board;
    private TemplateEngineTester testHelper;
    private TemplateEngine engine;
    private String gameID = "1234";
    private Replay replay;
    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    public void setup(){
        this.gson = new Gson();
        this.engine = mock(TemplateEngine.class);
        this.testHelper = new TemplateEngineTester();
        this.session = mock(Session.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.gameCenter = mock(GameCenter.class);

        this.playerServices = mock(PlayerServices.class);
        this.board = new Checkerboard();
        this.redPlayer = new Player("red");
        this.whitePlayer = new Player("white");
        this.replay = new Replay(new Game(redPlayer,whitePlayer));
        CuT = new GetReplayGameRoute(engine, gameCenter);

        when(request.queryParams(GetGameRoute.GAME_ID)).thenReturn(gameID);
        when(gameCenter.replayFromID(gameID)).thenReturn(replay);
        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);

        when(playerServices.getThisPlayer()).thenReturn(redPlayer);
        when(redPlayer.getName()).thenReturn("red");

        //when(playerServices.getThisPlayer().getName()).thenReturn("red");

        when(replay.hasNextMove()).thenReturn(true);
        when(replay.hasPrevMove()).thenReturn(true);
        when(replay.getRedPlayer().getName()).thenReturn("red");
        when(replay.getWhitePlayer().getName()).thenReturn("white");
        when(replay.isRedPlayerTurn()).thenReturn(true);
        when(replay.getBoard()).thenReturn(board);
        when(request.queryParams("play")).thenReturn("true");
        when(engine.render(any())).thenAnswer(testHelper.makeAnswer());
    }

    @Test
    public void modeOptions(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        Map<String,Object> modeOptionsAsJSON = new HashMap<>();
        modeOptionsAsJSON.put(GetReplayGameRoute.HAS_NEXT,true);
        modeOptionsAsJSON.put(GetReplayGameRoute.HAS_PREVIOUS,true);

        testHelper.assertViewModelAttribute(GetReplayGameRoute.MODE_OPTIONS_AS_JSON,gson.toJson(modeOptionsAsJSON));
    }

    @Test void players(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetReplayGameRoute.RED_PLAYER,"red");
        testHelper.assertViewModelAttribute(GetReplayGameRoute.WHITE_PLAYER,"white");
    }

    @Test
    public void activeColor(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetReplayGameRoute.ACTIVE_COLOR,"red");
    }

    @Test
    public void viewMode(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetReplayGameRoute.VIEW_MODE,GetReplayGameRoute.ViewMode.REPLAY);
    }

    @Test
    public void gameID(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetReplayGameRoute.GAME_ID,gameID);
    }

    @Test
    public void board(){

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetReplayGameRoute.BOARD_VIEW_KEY,new BoardView(board));
    }

}