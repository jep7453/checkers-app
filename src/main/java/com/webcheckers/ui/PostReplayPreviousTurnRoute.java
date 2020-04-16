package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/*
 * This  route shows for the previous turn made by the player in the game.
 * @author Kesa Abbas Lnu <kl3468@rit.edu>*/

public class PostReplayPreviousTurnRoute implements Route {

    static final String GAME_ID = "gameID";

    private Gson gson;


    public PostReplayPreviousTurnRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        //get playerServices from session
        PlayerServices playerServices = httpSession.attribute("playerServices");

        //gameID
        final String gameID = request.queryParams(GAME_ID);
        Message message = Message.info("false");
        Replay replay = playerServices.replayFromID(gameID);
        replay.setPlay(false);
        if(replay.makePrevTurn()) {
            message = Message.info("true");
        }
        return gson.toJson(message,Message.class);
    }
}
