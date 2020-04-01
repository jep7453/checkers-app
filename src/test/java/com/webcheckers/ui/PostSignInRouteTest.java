package com.webcheckers.ui;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Player;

import javax.servlet.http.HttpServletResponse;

/**
 * This is a test suite for the @Link GetSignInRoute class
 * @author Jonathan Pofcher
 */
@Tag("UI-tier")
public class PostSignInRouteTest {
    private static final String PLAYER1_NAME = "abcdefg1";

    public static final String HELP_MSG = "Error: Please enter a correctly formated username";
    private PostSignInNameRoute CuT;

	// friendly objects
	private Player player1;

	// attributes holding mock objects
	private static Request request;
	private static Session session;
	private static Response response;
	private static TemplateEngine engine;
    private static GameCenter gameCenter;
    private static PlayerLobby playerLobby;
    private static PlayerServices playerServices;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
		session = mock(Session.class);
		engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        gameCenter = new GameCenter();
        playerLobby = mock(PlayerLobby.class);
        playerServices = new PlayerServices(gameCenter);
		player1 = new Player(PLAYER1_NAME);
		when(request.session()).thenReturn(session);
		CuT = new PostSignInNameRoute(gameCenter, engine);
    }

   @Test
	public void signInValidUsername() {
        // Set up mocks
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("aaa1aaa");
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());
        when(session.attribute("playerServices")).thenReturn(playerServices);

        // do the thing
        try {
            CuT.handle(request, response);
            fail("Did not reach halt");
        } catch (HaltException e){
            // it worked
        }
    }

    /**
     * Test the username is not greater than 25 characters
     */
    @Test
    public void greater25Characters() {
        // Set up mocks
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());

        // do the thing
        CuT.handle(request, response );

        // make sure view model exists and is a map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // see if the sign in page is there with the correct error message
        testHelper.assertViewName("signin.ftl");
        testHelper.assertViewModelAttribute(PostSignInNameRoute.MESSAGE_TYPE_ATTR, PostSignInNameRoute.HELP_MSG);
    }

    /**
     * Username is greater than or equal to 6 characters
     */
    @Test
    public void lessThan6Characters(){
        // Set up mocks
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("aaa");
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());

        // do the thing
        CuT.handle(request, response );

        // make sure view model exists and is a map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // see if the sign in page is there with the correct error message
        testHelper.assertViewName("signin.ftl");
        testHelper.assertViewModelAttribute(PostSignInNameRoute.MESSAGE_TYPE_ATTR, PostSignInNameRoute.HELP_MSG);
    }

    /**
     * username must contain a number, test that is fails when it doesnt
     */
    @Test
    public void doesNotContainNumber(){
        // Set up mocks
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("aaaaaaa");
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());

        // do the thing
        CuT.handle(request, response );

        // make sure view model exists and is a map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // see if the sign in page is there with the correct error message
        testHelper.assertViewName("signin.ftl");
        testHelper.assertViewModelAttribute(PostSignInNameRoute.MESSAGE_TYPE_ATTR, PostSignInNameRoute.HELP_MSG);
    }

    /**
     * check failure when there is a number at the beginning
     */
    @Test
    public void numberAtBeginning(){
        // Set up mocks
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("1aaaaaa");
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());

        // do the thing
        CuT.handle(request, response );

        // make sure view model exists and is a map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // see if the sign in page is there with the correct error message
        testHelper.assertViewName("signin.ftl");
        testHelper.assertViewModelAttribute(PostSignInNameRoute.MESSAGE_TYPE_ATTR, PostSignInNameRoute.HELP_MSG);
    }

    @Test
    public void alreadyTaken(){
        // Set up mocks
        String name = "aaa1aaa";
        Player player = new Player(name);
        gameCenter.signIn(player);
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn(name);
        when(engine.render(any(ModelAndView.class))).then(testHelper.makeAnswer());
        when(session.attribute("playerServices")).thenReturn(playerServices);

        // do the thing
        CuT.handle(request, response );

        // make sure view model exists and is a map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // see if the sign in page is there with the correct error message
        testHelper.assertViewName("signin.ftl");
        testHelper.assertViewModelAttribute(PostSignInNameRoute.MESSAGE_TYPE_ATTR, "Error: This username has already been taken");
    }
}