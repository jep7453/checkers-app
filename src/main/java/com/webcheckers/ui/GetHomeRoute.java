package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private static final String CURRENT_USER= "currentUser";
  private static final String TOTAL_PLAYERS="totalPlayers";
  private static final String PLAYER_LIST="playerList";

  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final GameCenter gameCenter, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter=gameCenter;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");


    PlayerLobby playerLobby = gameCenter.getLobby();

    final Session httpSession = request.session();
    //get playerServices from session
    PlayerServices playerServices = httpSession.attribute("playerServices");
    //if none exists, make PlayerSession
    if(playerServices==null) {
      playerServices = new PlayerServices(gameCenter);
      httpSession.attribute("playerServices",playerServices);
    }


    // Check if the player is signed in and populate the correct attributes
    if (playerServices.isSignedIn()) {
      Player currentUser = playerServices.getThisPlayer();
      vm.put("currentUser",currentUser.getName() );
      vm.put("playerList",playerLobby.getPlayersNames(currentUser));

    }
    else {
      vm.put("totalPlayers",playerLobby.getTotalPlayers());
    }
    // display a user message in the Home page
    if(httpSession.attribute("error")!=null) {
      vm.put("message",Message.info(httpSession.attribute("error")));
    }
    else {
      vm.put("message", WELCOME_MSG);
    }

    if( gameCenter.isCurrentlyPlaying(playerServices.getThisPlayer())){
      response.redirect(WebServer.GAME_URL);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }



}
