package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Replay;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.halt;

/*
 * This is the route for the stopping to watch the replay.
 * @author Kesa Abbas Lnu <kl3468@rit.edu>*/

public class GetReplayStopWatchingRoute implements Route {

    static final String GAME_ID = "gameID";

    private final GameCenter gameCenter;

    public GetReplayStopWatchingRoute(GameCenter gameCenter) {
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        //gameID
        final String gameID = request.queryParams(GAME_ID);
        for(Replay replay : gameCenter.getReplaysWatched()){
            if(replay.getGameID() == gameID){
                gameCenter.getReplaysWatched().remove(replay);
                response.redirect(WebServer.HOME_URL);
                halt();
                return null;
            }
        }
        return null;

    }
}
