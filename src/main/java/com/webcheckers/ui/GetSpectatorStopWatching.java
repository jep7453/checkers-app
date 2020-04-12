package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

/**
 * Stop watching a game and redirect to home
 * @author Chris Tremblay
 */
public class GetSpectatorStopWatching implements Route {
    /** GameID parameter */
    private static final String GAME_ID = "gameID";

    /** The game center */
    private GameCenter gameCenter;

    /**
     * Create a new route
     * @param gameCenter the game center
     */
    public GetSpectatorStopWatching(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // get session, playerServices, gameID, and game
        Session session = request.session();
        PlayerServices playerServices = session.attribute(GetGameRoute.PLAYER_SERVICES_ATTR);
        String gameid = request.queryParams(GAME_ID);
        Game game = gameCenter.gameFromID(gameid);

        // tell game player stopped spectating
        game.stoppedSpectating(playerServices.getThisPlayer());
        response.redirect(WebServer.HOME_URL);
        halt();
        return (null);
    }
}
