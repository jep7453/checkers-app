package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Route;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The UI Controller to POST move validation.
 *
 * @author <a href='mailto:jep7453@rit.edu'>Jonathan Pofcher</a>
 */

public class PostSubmitTurnRoute implements Route {


  private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostSubmitTurnRoute.class.getName());
  private Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
   *
   * @param gson
   */
  public PostSubmitTurnRoute(Gson gson) {
    this.gson=gson;
    LOG.config("PostSubmitTurnRoute is initialized.");
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
    LOG.finer("PostSubmitTurnRoute is invoked.");
    Message turnMessage;

    Session httpSession =request.session();
    PlayerServices playerServices = httpSession.attribute("playerServices");
    Game game = playerServices.currentGame();
    if(!game.isValidTurn())
    {
      turnMessage = Message.error("Move Invalid");
      return gson.toJson(turnMessage,Message.class);
    }
    turnMessage = Message.info("Move Valid");

    return gson.toJson(turnMessage,Message.class);
  }
}
