package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to POST the SignIn page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSignInNameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    public static final String USER_NAME = "userName";
    public static final String USER_PARAM = "myUserName";
    public static final String CURRENT_USER= "currentUser";
    public static final String MESSAGE_TYPE_ATTR = "message";
    public static final String ERROR_TYPE = "error";
    public static final String TITLE= "title";
    public static final String TITLE_MSG= "Welcome!";
    public static final String HELP_MSG = "Error: Please enter a correctly formatted username";
    public static final String NAME_ALREADY_TAKE = "Error: This username is already taken";
    public static final String VIEW_NAME = "signin.ftl";
    public static final String GAME_WON = "gameWon";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignInNameRoute(final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("PostSignInNameRoute is initialized.");
        this.gameCenter=gameCenter;
    }

    /**
     * This function checks the validity of a username based on there
     * requirements:
     *     - username should be at least
     *       6 characters but less or equal to 25
     *     - character must contain at least 1 number
     *       but it cannot be the first character
     *     - follow other criteria from previous sprint
     *
     * @param username the username to check
     * @return true if username is valid, false if not
     */
    private boolean userNameValid(final String username){
        // assume username is valid
        boolean isValid = true;

        if(username.equals("")){
            return (false);
        }

        // check length requirements. 6 <= len <= 25 chars
        if (username.length() < 6 || username.length() > 25)
            isValid = false;

        // John stuff for original requirements
        if(!username.matches("^[ a-zA-Z0-9]*$") || username.matches("^[ ]*$") || username.isEmpty())
            isValid = false;

        // first char cannot be number, but username should contain a number
        if(Character.isDigit(username.charAt(0)))
            isValid = false;

        boolean hasNum = false;
        for(int i = 1; i < username.length(); i++){
            if(Character.isDigit(username.charAt(i))){
                hasNum = true;
                break;
            }
        }

        if(!hasNum)
            isValid = false;

        return (isValid);
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
        LOG.finer("PostSignInName is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, TITLE_MSG);

        final Session httpSession = request.session();

        //retrieve playerServices
        final PlayerServices playerServices = httpSession.attribute(GetHomeRoute.PLAYER_SERVICES);

        // retrieve username assume username is valid until
        // it fails from checks
        final String userName = request.queryParams(USER_PARAM);

        //error check username
        if( !userNameValid(userName) ) {
            vm.put(MESSAGE_TYPE_ATTR, Message.info(HELP_MSG));
            return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
        }

        //create player with input userName
        final Player player = new Player(userName.trim());

        //error check playerlobby
        if(gameCenter.isSignedIn(player)) {
            vm.put(MESSAGE_TYPE_ATTR, Message.info(NAME_ALREADY_TAKE));
            return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
        }
        httpSession.attribute(GAME_WON,false);
        playerServices.signIn(player.getName());
        // render the View
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
