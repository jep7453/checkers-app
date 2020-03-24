package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Checkerboard;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.junit.jupiter.api.Assertions.*;

/***
 * This is unit test for BoardView class of UI Tier.
 * @author Kesa Abbas Lnu <kl3468rit.edu>
 */

class GetGameRouteTest {

    private GetGameRoute CuT;

    //friendly objects
    private GameCenter gameCenter;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;
    private Checkerboard checkerboard;
    private BoardView boardView;

    //mock objects
    private Request request;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerServices playerServices;


    private static final String TITLE = "test_title";
    private static final String GAME_ID = "123456";
    private static final String MESSAGE = "test_message";
    private static final String RED_PLAYER = "test_red";
    private static final String WHITE_PLAYER = "test_white";
    private static final String VIEW_MODE = "test_viewMode";
    private static final String ACTIVE_COLOR = "test_activeColor";
    private static final String GAME_OVER_MESSAGE = "test_gameOverMessage";

    private static final String CHECK_CURRENT_PLAYER = "\"currentUser\" : " + RED_PLAYER;
    private static final String CHECK_RED_PLAYER = "\"redPlayer\" : " + RED_PLAYER;
    private static final String CHECK_WHITE_PLAYER = "\"whitePlayer\" : " + WHITE_PLAYER;
    private static final String CHECK_GAME_ID = "\"gameID\" : " + GAME_ID;
    private static final String CHECK_VIEW_MODE = "\"viewMode\" : " + VIEW_MODE;
    private static final String CHECK_MODE_OPTIONS_AS_JSON = "\"modeOptions\" : ";
    private static final String CHECK_ACTIVE_COLOR = "\"activeColor\" : " + ACTIVE_COLOR;


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        playerServices = mock(PlayerServices.class);

        gameCenter = new GameCenter();
        redPlayer = new Player(RED_PLAYER);
        whitePlayer = new Player(WHITE_PLAYER);
        game = gameCenter.getGame(redPlayer,whitePlayer);
        checkerboard = new Checkerboard();
        boardView = new BoardView(checkerboard);


        when(playerServices.currentGame()).thenReturn(game);
        when(session.attribute(GetGameRoute.BOARD_VIEW_KEY)).thenReturn(boardView);

        CuT = new GetGameRoute(templateEngine,gameCenter);

    }

    @Test
    public void test() {

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetGameRoute.VIEW_NAME);
        // setup View-Model for a new player
        vm.put(GetGameRouteTest.TITLE, TITLE);
        vm.put(GetGameRouteTest.GAME_ID, GAME_ID);
        vm.put(GetGameRouteTest.CHECK_CURRENT_PLAYER, redPlayer);
        vm.put(GetGameRoute.VIEW_MODE, VIEW_MODE);
        //if game has ended
        boolean gameHasEnded = true;
        Map<String, Object> modeOptionsAsJSON = null;
        if (gameHasEnded) {
            modeOptionsAsJSON = new HashMap<String, Object>();
            modeOptionsAsJSON.put("isGameOver", true);
            modeOptionsAsJSON.put("gameOverMessage", "test_gameOverMessage");
            vm.put(GetGameRoute.MODE_OPTIONS_AS_JSON, modeOptionsAsJSON);
        }

        vm.put(GetGameRoute.RED_PLAYER, redPlayer);
        vm.put(GetGameRoute.WHITE_PLAYER, whitePlayer);

        //if red player turn then active color is RED
        boolean idRedPlayerTurn = true;
        if (idRedPlayerTurn) {
            vm.put(GetGameRoute.ACTIVE_COLOR, ACTIVE_COLOR);
        }
        vm.put(GetGameRoute.BOARD_VIEW_KEY, boardView);
        vm.put(GetGameRouteTest.MESSAGE, MESSAGE);

        final String viewHtml = templateEngine.render(modelAndView);

        if (viewHtml != null) {
            assertTrue(viewHtml.contains(TITLE), "Title exists.");

            assertTrue(viewHtml.contains(CHECK_GAME_ID), "GameID exists.");
            assertTrue(viewHtml.contains(CHECK_CURRENT_PLAYER), "Current user exists.");
            assertTrue(viewHtml.contains(CHECK_RED_PLAYER), "Red player exists.");
            assertTrue(viewHtml.contains(CHECK_WHITE_PLAYER), "White player exists.");
            assertTrue(viewHtml.contains(CHECK_VIEW_MODE), "View Mode exists");
            assertTrue(viewHtml.contains(CHECK_ACTIVE_COLOR), "Active Color exists");
            assertTrue(viewHtml.contains(CHECK_MODE_OPTIONS_AS_JSON + modeOptionsAsJSON.toString()), "Mode options exists");
        }



}}