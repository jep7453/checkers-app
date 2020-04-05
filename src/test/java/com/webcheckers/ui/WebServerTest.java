package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

/**
 * Unit test for webserver
 * @author Chris Tremblay
 */
@Tag("ui")
public class WebServerTest {

    private WebServer CuT;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private Gson gson;

    @BeforeEach
    public void setup(){
        this.engine = mock(TemplateEngine.class);
        this.gameCenter = mock(GameCenter.class);
        this.gson = new Gson();
        this.CuT = new WebServer(gameCenter, engine, gson);
    }

    /**
     * Make sure web server runs with no errors
     */
    @Test
    public void runWebServer(){
        CuT.initialize();
    }
}
