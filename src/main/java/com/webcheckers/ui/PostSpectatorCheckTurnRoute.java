package com.webcheckers.ui;

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

        response.redirect(WebServer.HOME_URL);
        halt();
        return (null);
    }
}
