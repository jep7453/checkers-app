package com.webcheckers.ui;

import com.webcheckers.model.Checkerboard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("model")
public class NoMovesTest {

    private Checkerboard checkerboard;

    @Test
    public void cantMove(){
        checkerboard = new Checkerboard("noMovesSingle.txt");
    }
}
