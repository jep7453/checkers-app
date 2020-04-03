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


import java.util.logging.Logger;


/**
 * The UI Controller to POST backup Move
 * @author <a href='mailto:jep7453@rit.edu'>Jonathan Pofcher</a>
 */

public class PostBackUpMoveRoute implements Route {


  private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostBackUpMoveRoute.class.getName());
  private Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
   *
   * @param gson
   */
  public PostBackUpMoveRoute(Gson gson) {
    this.gson=gson;
    LOG.config("PostBackUpMoveRoute is initialized.");
  }

  /**
   * Back up a move.
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
    LOG.finer("PostBackUpMoveRoute is invoked.");
    Message backMessage;
    Session httpSession =request.session();
    PlayerServices playerServices = httpSession.attribute("playerServices");
    Game game = playerServices.currentGame();
    game.backUpMove();
    backMessage=Message.info("Last move undone");

    return gson.toJson(backMessage,Message.class);
  }
}