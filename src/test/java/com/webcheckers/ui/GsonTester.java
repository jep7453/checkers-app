package com.webcheckers.ui;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;

public class GsonTester {

    private String json;

    public Answer<Object> makeAnswer() {
        return new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final String json = invocation.getArgument(0);
                GsonTester.this.json = json;
                // the return value is not of interest
                return null;
            }
        };
    }

    public void assertJsonNotNull(){
        assertNotNull(json, "Json string exists");
    }

    public void assertIsString(){
        assertSame(json.getClass(), String.class);
    }

    public String getJson(){
        return json;
    }
}
