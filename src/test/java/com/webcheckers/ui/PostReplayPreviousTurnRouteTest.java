package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostReplayPreviousTurnRouteTest {

    private Gson gson;
    private PostReplayPreviousTurnRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Response response;
    private Replay replay;

    private String gameID = "1234";
    /*
     * Test for the previous turn route for the replay class
     * @author: Kesa Abbas Lnu <kl3468@rit.edu>
     */


    @Test
    public void prevTurn(){
        this.gson = new Gson();
        this.gameCenter = mock(GameCenter.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.replay = mock(Replay.class);
        CuT = new PostReplayPreviousTurnRoute(gameCenter,gson);

        when(request.queryParams(PostReplayNextTurnRoute.GAME_ID)).thenReturn(gameID);
        when(gameCenter.replayFromID(gameID)).thenReturn(replay);
        when(replay.makePrevTurn()).thenReturn(true);
        Message message = Message.info("true");
        assertTrue(CuT.handle(request,response).equals( gson.toJson(message,Message.class)));

    }

}