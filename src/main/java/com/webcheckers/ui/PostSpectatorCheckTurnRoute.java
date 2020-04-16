package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

/**
 * Route for checking for a turn
 */
public class PostSpectatorCheckTurnRoute implements Route {
    /** GameID param */
    private static final String GAME_ID = "gameID";
    private Gson gson;

    public PostSpectatorCheckTurnRoute(Gson gson) {
        this.gson = gson;
    }

    /**
     * Redirect to the home page
     * @param request request
     * @param response response
     * @return nothing
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();
        session.attribute(GetGameRoute.PLAYER_SERVICES_ATTR);
        request.queryParams(GAME_ID);

        Message message = Message.info("true");

        return gson.toJson(message, Message.class);
    }
}
