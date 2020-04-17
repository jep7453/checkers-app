package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Stop watching test suite
 * @author Chris Tremblay
 */
@Tag("ui")
public class GetSpectatorStopWatchingTest {
    private GameCenter gameCenter;
    private Request request;
    private Response response;
    private Session session;
    private PlayerServices playerServices;
    private Game game;
    private GetSpectatorStopWatchingRoute CuT;

    @BeforeEach
    public void setup(){
        this.gameCenter = mock(GameCenter.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.playerServices = mock(PlayerServices.class);
        this.game = mock(Game.class);
        this.CuT = new GetSpectatorStopWatchingRoute(gameCenter);

        when(request.session()).thenReturn(session);
        when(session.attribute(GetGameRoute.PLAYER_SERVICES_ATTR)).thenReturn(playerServices);
        when(request.queryParams(GetSpectatorStopWatchingRoute.GAME_ID)).thenReturn("1234");
    }

    @Test
    public void testRedirect(){
        when(gameCenter.gameFromID(anyString())).thenReturn(game);
        try{
            CuT.handle(request, response);
        } catch (Exception e){

        }

        verify(response, times(1)).redirect(anyString());
    }
}
