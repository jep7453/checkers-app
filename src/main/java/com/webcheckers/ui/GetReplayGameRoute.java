package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Replay;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * This is the get game route for the replay class.
 * @author Kesa Abbas Lnu <kl3468@rit.edu>
 * @author Scott Court <sxc4981@rit.edu>
 */

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
    static final String PARAM_ORIENTATION = "orientation";

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private  Replay replay;

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
    public Object handle(Request request, Response response) //throws Exception
    {

        Map<String, Object> vm = new HashMap<>();
	Player facingPlayer;
	final String orientation;

	//final 
        vm.put("title", "Welcome!");

        //Initialization
        final Session httpSession = request.session();
        final PlayerServices playerServices = httpSession.attribute("playerServices");

        //gameID
        final String gameID = request.queryParams(GAME_ID);
        if(playerServices.replayFromID(gameID)==null) {
            playerServices.addReplay(new Replay(gameCenter.replayFromID(gameID).getReplayGame()));
        }
        replay = playerServices.replayFromID(gameID);

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

        //Active Color
        if(replay.isRedPlayerTurn()){
            vm.put(ACTIVE_COLOR,ActiveColor.RED);
        }
        else {
            vm.put(ACTIVE_COLOR,ActiveColor.WHITE);
        }

        //Board
        vm.put(BOARD_VIEW_KEY,new BoardView(replay.getBoard()));

	//Orientation
        orientation = request.queryParams(PARAM_ORIENTATION);
	if ( orientation != null && orientation.equalsIgnoreCase("red") ) {
		facingPlayer = replay.getRedPlayer();
		vm.put("otherOrientation", "white");
	}
	else {
		facingPlayer = replay.getWhitePlayer();
		vm.put("otherOrientation", "red");
	}
	vm.put("currentUser", facingPlayer.getName());

        //message
        vm.put(MESSAGE_KEY, MESSAGE);
        vm.put(GAME_ID,gameID);

        boolean play = Boolean.parseBoolean(request.queryParams("play"));
        if(play&&replay.getPlay()) {
            replay.makeNextTurn();
            vm.put("play",true);
        }

        replay.setPlay(true);
        vm.put("moveCount",replay.getMoveCount());
        vm.put("movesMade",replay.getMovesMade());
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

