package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;


/**
 * The UI Controller to POST move validation.
 *
 * @author <a href='mailto:jep7453@rit.edu'>Jonathan Pofcher</a>
 */

public class PostCheckTurnRoute implements Route {


  private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());
  private Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
   *
   * @param gson
   */
  public PostCheckTurnRoute(Gson gson) {
    this.gson=gson;
    LOG.config("PostCheckTurnRoute is initialized.");
  }

  /**
   * Validate a checker movie.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   whether move is valid or not
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostCheckTurnRoute is invoked.");
    Message turnMessage;
    Session httpSession =request.session();
    PlayerServices playerServices = httpSession.attribute("playerServices");
    Game game = playerServices.currentGame();
    if(game.currentPlayer().equals(playerServices.getThisPlayer())||game.isGameWon()) {
      if(game.isGameWon()) {
        playerServices.setGameOver(true);
        game.resigned();
        playerServices.finishedGame();
      }
      turnMessage= Message.info("true");
    }
    else {
      turnMessage=Message.error("false");
    }
    return gson.toJson(turnMessage,Message.class);
  }
}

