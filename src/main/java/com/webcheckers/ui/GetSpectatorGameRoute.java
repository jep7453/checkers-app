package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * The Route for handling spectating a game
 *
 * @author Chris Tremblay
 */
public class GetSpectatorGameRoute implements Route {

    /** The game center */
    private GameCenter gameCenter;

    /** The engine */
    private TemplateEngine engine;

    private static final String MESSAGE = "Welcome!";

    static final String PARAM_ORIENTATION = "orientation";

    /**
     * Create a new GetGameSpectatorRoute
     * @param gameCenter
     * @param engine
     */
    public GetSpectatorGameRoute(GameCenter gameCenter, TemplateEngine engine){
        this.gameCenter = gameCenter;
        this.engine = engine;
    }

    /**
     * Handle request
     * @param request the request
     * @param response the response
     * @return the body of the response
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
// get important stuff from session and query parameters
        Session session = request.session();
        Player facingPlayer;
        PlayerServices playerServices = session.attribute("playerServices");
        String gameID = request.queryParams(GetGameRoute.GAME_ID);
        final String orientation;
        // get the game, make sure it isn’t null
        Game game = gameCenter.gameFromID(gameID);
        if(game == null){
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        // populate vm for game.ftl
        Map<String, Object> vm = new HashMap<>();
        //Orientation
        orientation = request.queryParams(PARAM_ORIENTATION);
        if ( orientation != null && orientation.equalsIgnoreCase("red") ) {
            facingPlayer = game.getRedPlayer();
            vm.put("otherOrientation", "white");
        }
        else {
            facingPlayer = game.getWhitePlayer();
            vm.put("otherOrientation", "red");
        }
        vm.put("currentUser", facingPlayer.getName());
        vm.put(GetGameRoute.TITLE, MESSAGE);
        vm.put("gameID",gameID);
        vm.put(GetGameRoute.VIEW_MODE, GetGameRoute.ViewMode.SPECTATOR);
        vm.put(GetGameRoute.RED_PLAYER, game.getRedPlayer().getName());
        vm.put(GetGameRoute.WHITE_PLAYER, game.getWhitePlayer().getName());
        if(game.getRedPlayer().equals(game.getCurrentPlayer()))
            vm.put(GetGameRoute.ACTIVE_COLOR, GetGameRoute.ActiveColor.RED);
        else
            vm.put(GetGameRoute.ACTIVE_COLOR, GetGameRoute.ActiveColor.WHITE);
        vm.put(GetGameRoute.BOARD_VIEW_KEY, new BoardView(game.getBoard()));
        game.startedSpectating(playerServices.getThisPlayer());
        // render
        return engine.render(new ModelAndView(vm, GetGameRoute.VIEW_NAME));
    }
}
