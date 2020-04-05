package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Resign page.
 *
 * @author Scott Court</a>
 */
public class PostResignRoute implements Route {
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
    public PostResignRoute(final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("PostResignRoute is initialized.");
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
        LOG.finer("PostResign is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        Map<String, Object> options = new HashMap<>();
        options.put("isGameOver", true);
        options.put("gameOverMessage", "test");

        final Session httpSession = request.session();

        //retrieve playerServices
        final PlayerServices playerServices = httpSession.attribute("playerServices");

        return null;
    }
}
