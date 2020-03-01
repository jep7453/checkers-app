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

                // Initialize player and game objects
                Player redPlayer;
                Player whitePlayer;
                Game game;

                // Check to see if there is currently a game
                if(gameCenter.isCurrentlyPlaying(playerServices.getThisPlayer())){
                        game = playerServices.currentGame();

                        // If the current player is the white player in the game object
                        // set respective variables
                        if(playerServices.getThisPlayer().equals(game.getWhitePlayer())){
                                playerServices.setOpponent(game.getRedPlayer().getName()); // set opponent
                                redPlayer = playerServices.getOpponent(); // red player is the opponent
                                whitePlayer = playerServices.getThisPlayer(); // this player is the white player
                        } else {
                                // if the player is the red player in the game object
                                // set respective variables
                                playerServices.setOpponent(game.getWhitePlayer().getName()); // set opponent
                                redPlayer = playerServices.getThisPlayer(); // we are the red player
                                whitePlayer = playerServices.getOpponent(); // the opponent is the white player
                        }
                } else { // else there is no current game
                        //redPlayer
                        redPlayer = playerServices.getThisPlayer();

                        //whitePlayer
                        whitePlayer = playerServices.getOpponent();
                }

                // populate vm
                vm.put(RED_PLAYER, redPlayer.getName());
                vm.put(WHITE_PLAYER, whitePlayer.getName());

                game = playerServices.currentGame(); //incase we didn't already retreive it

                //activeColor
                boolean idRedPlayerTurn;
                if(game.getCurrentPlayer().equals(redPlayer)){
                        idRedPlayerTurn = true;
                } else{
                        idRedPlayerTurn = false;
                }

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

