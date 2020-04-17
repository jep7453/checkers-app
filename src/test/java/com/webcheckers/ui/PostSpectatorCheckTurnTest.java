package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerServices;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("ui")
public class PostSpectatorCheckTurnTest {
    private Gson gson;

    private Request request;

    private Response response;

    private Session session;

    private PlayerServices playerServices;

    private PostSpectatorCheckTurnRoute CuT;

    @BeforeEach
    public void setup(){
        this.gson = new Gson();
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.playerServices = mock(PlayerServices.class);
        this.CuT = new PostSpectatorCheckTurnRoute(gson);
    }

    @Test
    public void handleTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(anyString())).thenReturn(playerServices);
        Message msg = Message.info("true");
        Object testMsg = CuT.handle(request, response);
        System.out.println(testMsg);
        assertEquals(gson.toJson(msg), testMsg);
    }
}
