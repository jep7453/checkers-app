package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to POST the SignIn page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSignInNameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    private static final String USER_NAME = "userName";
    private static final String USER_PARAM = "myUserName";
    private static final String CURRENT_USER= "currentUser";
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
        vm.put("title", "Welcome!");

        final Session httpSession = request.session();

        // retrieve username
        final String userName = request.queryParams(USER_PARAM);

        //error check username
        if(userName == null || !userName.matches("^[ a-zA-Z0-9]*$")  || userName.matches("^[ ]*$") || userName=="") {
            vm.put("message", Message.info("Error: Please enter a correctly formated username"));
            return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

        }
        //create player with input userName
        final Player player = new Player(userName);

        //error check playerlobby
        if(gameCenter.isSignedIn(userName)) {
            vm.put("message", Message.info("Error: This username has already been taken"));
            return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
        }

        httpSession.attribute("currentUser",player);
        gameCenter.signIn(userName);
        // render the View
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
