package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * This is the get game route for the replay class.
 * @author Kesa Abbas Lnu <kl3468@rit.edu>*/

public class GetReplayGameRoute implements Route {

    static final String GAME_ID = "gameID";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String VIEW_MODE = "viewMode";
    static final String ACTIVE_COLOR = "activeColor";
    static final String BOARD_VIEW_KEY = "board";
    static final String MESSAGE_KEY = "message";
    static final Message MESSAGE =Message.info("Replay");
    static final String MODE_OPTIONS_AS_JSON = "modeOptionsAsJSON";
    static final String HAS_NEXT = "hasNext";
    static final String HAS_PREVIOUS = "hasPrevious";

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    public GetReplayGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
    }

    public enum ViewMode{
        PLAY, SPECTATOR, REPLAY
    }

    public enum ActiveColor{
        RED, WHITE
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");


        //gameID
        final String gameID = request.queryParams(GAME_ID);
        Replay replay = gameCenter.replayFromID(gameID);

        //currentUser
        final Session httpSession = request.session();
        final PlayerServices playerServices = httpSession.attribute("playerServices");
        vm.put("currentUser", playerServices.getThisPlayer().getName());

        //modeOptionsAsJSON
        Map<String,Object> modeOptionsAsJSON = new HashMap<>();
        modeOptionsAsJSON.put(HAS_NEXT,replay.hasNextMove());
        modeOptionsAsJSON.put(HAS_PREVIOUS,replay.hasPrevMove());
        Gson gson = new Gson();
        vm.put(MODE_OPTIONS_AS_JSON,gson.toJson(modeOptionsAsJSON));

        //viewMode
        vm.put(VIEW_MODE,ViewMode.REPLAY);

        //Replay Players
        vm.put(RED_PLAYER, replay.getRedPlayer().getName());
        vm.put(WHITE_PLAYER, replay.getWhitePlayer().getName());

        //Avtive Color
        if(replay.isRedPlayerTurn()){
            vm.put(ACTIVE_COLOR,ActiveColor.RED);
        }
        else {
            vm.put(ACTIVE_COLOR,ActiveColor.WHITE);
        }

        //Board
        vm.put(BOARD_VIEW_KEY,new BoardView(replay.getBoard()));

        //message
        vm.put(MESSAGE_KEY, MESSAGE);

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
