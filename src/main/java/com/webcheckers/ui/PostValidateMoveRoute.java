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
 * The UI Controller to POST move validation.
 *
 * @author <a href='mailto:jep7453@rit.edu'>Jonathan Pofcher</a>
 */

public class PostValidateMoveRoute implements Route {


    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostValidateMoveRoute.class.getName());
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param gson
     */
    public PostValidateMoveRoute(Gson gson) {
      this.gson=gson;
      LOG.config("PostValidateMoveRoute is initialized.");
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
      LOG.finer("PostValidateMoveRoute is invoked.");
      Message moveMessage;
      System.out.println(request.contentType());

      System.out.println(request.body());

      final Move move = gson.fromJson(request.body(),Move.class);

      Session httpSession =request.session();
      PlayerServices playerServices = httpSession.attribute("playerServices");
      Game game = playerServices.currentGame();
      move.setType(game.isValidMove(move));
        if(move.getType()!= Move.Type.INVALID) {
          game.makeMove(move);
          moveMessage = Message.info("Move made");
        }
        else {
          moveMessage = Message.error("Move Invalid");
        }
        return gson.toJson(moveMessage,Message.class);
    }
  }



