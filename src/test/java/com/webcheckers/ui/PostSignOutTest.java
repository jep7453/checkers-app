package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Post sign out test unit test suite
 */
@Tag("ui")
public class PostSignOutTest {

    /** The component under test */
    PostSignOutRoute CuT;

    /** The request */
    private Request request;

    private Response response;
    private TemplateEngine engine;
    private PlayerServices playerServices;
    private Session session;
    private GameCenter gameCenter;
    private Game game;

    /**
     * Set up enviroment
     */
    @BeforeEach
    public void setup() throws Exception {
        this.session = mock(Session.class);
        this.request = mock(Request.class);
        this.session = mock(Session.class);
        this.response = mock(Response.class);
        this.gameCenter = mock(GameCenter.class);
        this.game = mock(Game.class);
        this.playerServices = mock(PlayerServices.class);
        CuT = new PostSignOutRoute(gameCenter, engine);

        when(request.session()).thenReturn(session);
    }

    /**
     * If player services is null
     */
    @Test
    public void playerServicesIsNull(){
        when(session.attribute("playerServices")).thenReturn(null);
        try {
            CuT.handle(request,response);
            fail();
        } catch (HaltException e){
            verify(response, atLeastOnce()).redirect(anyString());
        }
    }

    /**
     * player is already signed out
     */
    public void playerServicesAlreadySignedOut(){
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.isSignedIn()).thenReturn(false);
        try {
            CuT.handle(request,response);
            fail();
        } catch (HaltException e){
            verify(response, atLeastOnce()).redirect(anyString());
        }
    }

    /**
     * player successfully signed out
     */
    @Test
    public void successfulSignOut(){
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.isSignedIn()).thenReturn(true);
        Player player = mock(Player.class);
        when(playerServices.getThisPlayer()).thenReturn(player);
        when(player.getName()).thenReturn("testName");

        try {
            CuT.handle(request,response);
            fail();
        } catch (HaltException e){
            verify(response, atLeastOnce()).redirect(anyString());
        }
    }
}
