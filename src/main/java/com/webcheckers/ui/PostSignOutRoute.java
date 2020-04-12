package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostSignOutRoute implements Route {
    /** the log */
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    /** The game center */
    private GameCenter gameCenter;

    /** The freemarker engine */
    private TemplateEngine engine;

    public PostSignOutRoute(final GameCenter gameCenter, final TemplateEngine engine){
        this.gameCenter = gameCenter;
        this.engine = engine;
    }

    /**
     * What to do when the route is called
     * @param request the request
     * @param response the response
     * @return hopefully nothing
     */
    @Override
    public Object handle(Request request, Response response)  {
        LOG.finer("PostSignOutRouteInvoked");
        // Get the session
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        // make sure player services exists
        if( playerServices == null ) {
            response.redirect(WebServer.HOME_URL);
            halt();
            return (null);
        }

        // if the player already isn't signed in for some reason
        if(!playerServices.isSignedIn()) {
            response.redirect(WebServer.HOME_URL);
            halt();
            return (null);
        }

        if(gameCenter.isCurrentlyPlaying(playerServices.getThisPlayer())){
            Game game = playerServices.currentGame();
            game.resigned();
            playerServices.setGameOver(true);
            gameCenter.gameFinished(game);
            playerServices.finishedGame();
        }

        // else there is a player services object that exists
        // and has a signed in player
        LOG.finer("Player '" + playerServices.getThisPlayer().getName() + "' attempting to sign out");
        playerServices.signOut();
        LOG.finer("Player successfully signed out");
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
