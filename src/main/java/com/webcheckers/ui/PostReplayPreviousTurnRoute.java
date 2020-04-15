package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/*
 * This  route shows for the previous turn made by the player in the game.
 * @author Kesa Abbas Lnu <kl3468@rit.edu>*/

public class PostReplayPreviousTurnRoute implements Route {

    static final String GAME_ID = "gameID";

    private final GameCenter gameCenter;
    private Gson gson;


    public PostReplayPreviousTurnRoute(GameCenter gameCenter, Gson gson) {
        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //gameID
        final String gameID = request.queryParams(GAME_ID);
        Message message = Message.info("false");
        Replay replay = gameCenter.replayFromID(gameID);
        replay.setPlay(false);
        if(replay.makePrevTurn()) {
            message = Message.info("true");
        }
        return gson.toJson(message,Message.class);
    }
}
