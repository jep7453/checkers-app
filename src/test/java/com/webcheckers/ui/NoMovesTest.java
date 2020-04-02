package com.webcheckers.ui;

import com.webcheckers.model.Checkerboard;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@Tag("model")
public class NoMovesTest {

    private Checkerboard checkerboard;

    @Test
    public void cantMoveSingle(){
        checkerboard = new Checkerboard("noMovesSingle.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(3,4)));
    }
}
