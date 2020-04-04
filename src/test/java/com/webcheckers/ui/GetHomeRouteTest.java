package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a test suite for the @Link GetHomeRoute class
 * @author Chris Tremblay
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

    /** Static player name */
    private final String pid = "player123";

    /** opponent name */
    private final String oid = "opponent123";

    /** The home route handler */
    private GetHomeRoute CuT;

    /** Game center to keep track of what users are signed in */
    private GameCenter gameCenter;

    /** A player services object to be able to sign in */
    private PlayerServices playerServices;

    /** Request mock object */
    private Request request;

    /** A mock session */
    private Session session;

    /** A mock response */
    private Response response;

    /** A mock template engine class */
    private TemplateEngine engine;

    /** sign in tag */
    private static final String SIGN_IN_TAG = "<a href=\"/signin\">sign in</a>";

    /** title tag */
    private static final String TITLE_TAG = "<title>Web Checkers | Welcome!</title>";

    /** Error tag */
    private static final String ERR = "error";

    /**
     * helper engine
     */
    private TemplateEngineTester testHelper;

    /**
     * Creates a player tag like the one that would redirect you when you click a
     * name to start a game
     *
     * @param player player name
     * @return the formatted string
     */
    public static String makePlayerListTag(String player){
        return String.format("<a href=\"/game?player=%s\">%s</a>", player, player);
    }

    /**
     * Makes a sign out tag with player name, because it only shows up if they are logged in
     * @param player the player name
     * @return the formatted string
     */
    public static String makeSignOutTag(String player){
        return String.format("<a href=\"#\" onclick=\"event.preventDefault(); signout.submit();\">sign out [%s]</a>", player);
    }

    /**
     * Set up mock objects
     */
    @BeforeEach
    public void setup(){
        // Set up mock objects
        testHelper = new TemplateEngineTester();
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        engine = mock(FreeMarkerEngine.class);

        // initialize game center
        gameCenter = new GameCenter();

        // initialize player services
        playerServices = new PlayerServices(gameCenter);

        // initialize component under test
        CuT = new GetHomeRoute(gameCenter, engine);
    }

    /**
     * Test when no one is signed in that the page has a sign in tag and zero users playing
     */
    @Test
    public void renderHomeNoSignIn(){
        // initialize template engine tester

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        // load attributes
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE, GetHomeRoute.TITLE_MSG);
        testHelper.assertViewModelAttribute(GetHomeRoute.TOTAL_PLAYERS, 0);

        // render page

    }

    /**
     * Test when there are players playing and you are not playing there
     * is a sign in tag and the number of users is displayed
     */
    @Test
    public void noSignInOnePlayer(){
        // add a player to GameCenter
        gameCenter.signIn(new Player(oid));
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        // load attributes
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetHomeRoute.TOTAL_PLAYERS, 1);

        // render page


        // check that there are no players signed in and there is a sign in link
    }

    /**
     * Test if you are signed in but no one else is, that you see
     * your name in the sign out tag on the page
     */
    @Test
    public void playerSignedIn(){
        // mock sign in player
        playerServices.signIn(pid);
        PlayerLobby lobby = gameCenter.getLobby();
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, gameCenter.getLobby().getPlayersNames(playerServices.getThisPlayer()) );
    }

    @Test
    public void playerSignedInWithOtherPlayers(){
        // mock sign in player
        Player opponent = new Player(oid);
        playerServices.signIn(pid);
        gameCenter.signIn(opponent);
        PlayerLobby lobby = gameCenter.getLobby();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("playerServices")).thenReturn(playerServices);
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, lobby.getPlayersNames(playerServices.getThisPlayer()));


    }

    @Test
    public void thereIsAnErrorInSession(){
        String ERROR_MSG = "Error";
        when(session.attribute("error")).thenReturn(ERROR_MSG);
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());

        CuT.handle(request, response);

        // make sure the view model has an error in it
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //testHelper.assertViewModelAttribute("message", Message.info(ERROR_MSG));
    }

    @Test
    public void alreadyPlayingGame(){
        when(session.attribute("playerServices")).thenReturn(playerServices);
        playerServices.signIn(pid);
        gameCenter.playerStartedPlayingGame(playerServices.getThisPlayer());


        CuT.handle(request, response);

        verify(response, times(1)).redirect(anyString());

    }
}
