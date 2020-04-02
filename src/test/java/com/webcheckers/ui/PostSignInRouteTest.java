package com.webcheckers.ui;

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

/**
 * This is a test suite for the @Link GetSignInRoute class
 * @author Jonathan Pofcher
 */
@Tag("UI-tier")
public class PostSignInRouteTest {
    private static final String PLAYER1_NAME = "joe";

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

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
		session = mock(Session.class);
		engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        gameCenter = new GameCenter();
		PlayerLobby playerLobby = mock(PlayerLobby.class);
		player1 = new Player(PLAYER1_NAME);
		when(request.session()).thenReturn(session);
		CuT = new PostSignInNameRoute(gameCenter, engine);
    }

    @Test
	public void signInValidUsername() {
        
        CuT.handle(request, response);
        when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn(PLAYER1_NAME);
        verify(response, times(0)).redirect(WebServer.HOME_URL);
    }
    
    @Test
	public void signInInvalidUsername() {

        TemplateEngineTester testHelper = new TemplateEngineTester();
		when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("$hi");
        CuT.handle(request, response);

        final Map<String, Object> vm = new HashMap<>();
        vm.put("message", PostSignInNameRoute.HELP_MSG);
        final ModelAndView modelAndView = new ModelAndView(vm, "signin.ftl");
        final String html = engine.render(modelAndView);
    }

    @Test
	public void signInInvalidUsernameTwo() {

        TemplateEngineTester testHelper = new TemplateEngineTester();
		when(request.queryParams(PostSignInNameRoute.USER_PARAM)).thenReturn("&hello");
        CuT.handle(request, response);

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, "signin.ftl");
        vm.put("message", PostSignInNameRoute.HELP_MSG);
        final String html = engine.render(modelAndView);
    }
}