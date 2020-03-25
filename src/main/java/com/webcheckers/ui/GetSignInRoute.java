package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    public static final Message SIGN_IN_MSG = Message.info("Please enter a name.");
    public static final String MSG = "message";
    public static final String TITLE_MSG= "Sign In!";
    public static final String TITLE = "title";


    private final TemplateEngine templateEngine;

    public static final String VIEW_NAME = "signin.ftl";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers SignIn page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the SignIn page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, TITLE_MSG);

        // display a user message in the Home page
        vm.put(MSG, SIGN_IN_MSG);

        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
