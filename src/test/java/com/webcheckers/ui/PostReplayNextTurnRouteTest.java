package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/*
* Test for the next turn route for the replay class
* @author: Kesa Abbas Lnu <kl3468@rit.edu>
*/
class PostReplayNextTurnRouteTest {

    private Gson gson;
    private PostReplayNextTurnRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Response response;
    private Replay replay;
    private PlayerServices playerServices;
    private Session session;

    private String gameID = "1234";



    @Test
    public void nextTurn() throws Exception {
        this.gson = new Gson();
        this.gameCenter = mock(GameCenter.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.replay = mock(Replay.class);
        this.session = mock(Session.class);
        this.playerServices = mock(PlayerServices.class);
        CuT = new PostReplayNextTurnRoute(gson);

        when(request.session()).thenReturn(session);
        when(request.queryParams(PostReplayNextTurnRoute.GAME_ID)).thenReturn(gameID);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.replayFromID(gameID)).thenReturn(replay);
        when(replay.makeNextTurn()).thenReturn(true);
        Message message = Message.info("true");
        assertTrue(CuT.handle(request,response).equals(gson.toJson(message,Message.class)));

    }

}