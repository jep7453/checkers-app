package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/*
 * This route posts the next turn .
 * @author Kesa Abbas Lnu <kl3468@rit.edu>*/

public class PostReplayNextTurnRoute implements Route {

    static final String GAME_ID = "gameID";

    private final GameCenter gameCenter;
    private Gson gson;

    public PostReplayNextTurnRoute(GameCenter gameCenter,Gson gson) {
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        //gameID
        final String gameID = request.queryParams(GAME_ID);
        Message message = Message.info("false");
        for(Replay replayWatched : gameCenter.getReplaysWatched()){
            if(replayWatched.getGameID() == gameID){
                if(replayWatched.makeNextTurn()) message = Message.info("true");
            }
        }
        return gson.toJson(message,Message.class);
    }
}
