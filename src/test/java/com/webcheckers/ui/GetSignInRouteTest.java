package com.webcheckers.ui;


import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a test suite for the @Link GetSignInRoute class
 * @author Jonathan Pofcher
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

  /** Static player name */
  private final String pid = "player123";

  /** opponent name */
  private final String oid = "opponent123";

  /** The sign-in route handler */
  private GetSignInRoute CuT;

  /** Request mock object */
  private Request request;

  /** A mock session */
  private Session session;

  /** A mock response */
  private Response response;

  /** A mock template engine class */
  private TemplateEngine engine;

  /** sign in tag */
  private static final String SIGN_IN_TAG = "Please enter a name.";

  /** title tag */
  private static final String TITLE_TAG = "<title>Web Checkers | Sign In!</title>";

  /** Error tag */
  private static final String ERR = "error";

  /**
   * Set up mock objects
   */
  @BeforeEach
  public void setup(){
    // Set up mock objects
    request = mock(Request.class);
    session = mock(Session.class);
    response = mock(Response.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);

    // initialize component under test
    CuT = new GetSignInRoute(engine);
  }



  /**
   * Test that CuT shows the SignIn view properly.
   */
  @Test
  public void renderSignIn(){
    // To analyze what the Route created in the View-Model map you need
    // to be able to extract the argument to the TemplateEngine.render method.
    // Mock up the 'render' method by supplying a Mockito 'Answer' object
    // that captures the ModelAndView data passed to the template engine
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // Invoke the test
    CuT.handle(request, response);

    // Analyze the results:
    //   * model is a non-null Map
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    //   * model contains all necessary View-Model data
    testHelper.assertViewModelAttribute(GetSignInRoute.TITLE, GetSignInRoute.TITLE_MSG);
    testHelper.assertViewModelAttribute(GetSignInRoute.MSG, GetSignInRoute.SIGN_IN_MSG);
    //   * test view name
    testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
  }

  /**
   * Test that the page has correct html
   */
  @Test
  public void checkHTML(){
    // initialize template engine tester
    final TemplateEngine engine = new FreeMarkerEngine();

    final Map<String, Object> vm = new HashMap<>();
    final ModelAndView modelAndView = new ModelAndView(vm, GetSignInRoute.VIEW_NAME);

    // load attributes
    vm.put(GetSignInRoute.MSG, GetSignInRoute.SIGN_IN_MSG);
    vm.put(GetSignInRoute.TITLE, GetSignInRoute.TITLE_MSG);

    // render page
    final String html = engine.render(modelAndView);

    // check that the messages are there
    assertTrue(html.contains(SIGN_IN_TAG), "Sign In message exists");
    assertTrue(html.contains(TITLE_TAG), "Title tag exists");
  }
}
