package com.webcheckers.ui;

import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;


public class GetGameRoute implements Route {

        private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

        static final String TITLE_ATTR = "Web Checkers";
        static final String GAME_ID = "gameID";
        static final String RED_PLAYER = "redPlayer";
        static final String WHITE_PLAYER = "whitePlayer";
        static final String ACTIVE_COLOR = "activeColor";
        static final String MODE_OPTIONS_AS_JSON = "modeOptionsAsJSON";
        static final String VIEW_MODE = "viewMode";
        private final TemplateEngine templateEngine;
        static final String VIEW_NAME = "game.ftl";
        static final String BOARD_VIEW_KEY = "board";
        private static final Message WELCOME_MSG = Message.info("Lets begin the game.");
        private final GameCenter gameCenter; // game center

        public static final String HOME_URL = "/";

        /**
         * The URL pattern to request the Sign in Page.
         */
        public static final String SIGN_IN_URL = "/signin";
        /**
         * The URL pattern to post a username.
         */
        public static final String SIGN_IN_NAME_URL = "/signinname";
        private static final String GAME_ROUTE = "/get_game";
        public enum ViewMode{
                PLAY, SPECTATOR, REPLAY
        }

        public enum ActiveColor{
                RED, WHITE
        }


        public GetGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {

                Objects.requireNonNull(templateEngine, "templateEngine must not be null");

                this.templateEngine = templateEngine;
                this.gameCenter = gameCenter;

                LOG.config("GetGameRoute is initialized.");
        }


        @Override
        public Object handle(Request request, Response response) {
                LOG.finer("GetGameRoute is invoked.");


                //title
                Map<String, Object> vm = new HashMap<>();
                vm.put("title", "Welcome!");


                //gameID
                final String gameID = request.queryParams(GAME_ID);
                vm.put(GAME_ID,gameID);


                //currentUser
                final Session httpSession = request.session();
                final PlayerServices playerServices = httpSession.attribute("playerServices");
                vm.put("currentUser", playerServices.getThisPlayer().getName());
                String opponentName = request.queryParams("player");
                playerServices.setOpponent(opponentName);


                //viewMode
                vm.put(VIEW_MODE,ViewMode.PLAY);


                //modeOptionsAsJSON
                boolean gameHasEnded = false; //todo: initialise gameHasEnded
                if(gameHasEnded){
                        Map<String,Object> modeOptionsAsJSON = new HashMap<String, Object>();
                        modeOptionsAsJSON.put("isGameOver",true);
                        modeOptionsAsJSON.put("gameOverMessage","A String representing how the game ended. Such as: Bryan has captured all of the pieces.");
                        vm.put(MODE_OPTIONS_AS_JSON,modeOptionsAsJSON);
                }


                //redPlayer
                Player redPlayer = playerServices.getThisPlayer();
                vm.put(RED_PLAYER,redPlayer.getName());


                //whitePlayer
                Player whitePlayer = playerServices.getOpponent();
                vm.put(WHITE_PLAYER,whitePlayer.getName());

                Game game = playerServices.currentGame();
                if(game == null){
                        System.err.println("game is null");
                }


                //activeColor
                boolean idRedPlayerTurn = true; // todo: initiallise isRedPlayerTurn depending on the players turn
                if(idRedPlayerTurn){
                        vm.put(ACTIVE_COLOR,ActiveColor.RED);
                }
                else {
                        vm.put(ACTIVE_COLOR,ActiveColor.WHITE);
                }


                //board

                //NOTE: I have changed BoardView Class because I think this is correct
                vm.put(BOARD_VIEW_KEY,new BoardView(game.getBoard()));


                //message
                vm.put("message", WELCOME_MSG);

                //todo: use FreeMaker Template to fillup game.ftl
                return templateEngine.render(new ModelAndView(vm , "game.ftl"));
                //return gameCenter.getGame(redPlayer, whitePlayer);
        }
}

