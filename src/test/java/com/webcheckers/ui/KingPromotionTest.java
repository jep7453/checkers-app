package com.webcheckers.ui;

import com.webcheckers.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

@Tag("model")
public class KingPromotionTest {
    private Game game;
    private Checkerboard board;

    @BeforeEach
    void setup() {
        game = new Game(new Player("Red"), new Player("White"));
        board = game.getBoard();
    }

    @Test
    public void kingPromoTestForWhite(){
        KingPromotion KP = new KingPromotion(game);
        Square KingSpace = board.getSquare(7, 0);
        assertTrue(KP.shouldBeKinged(KingSpace.getChecker()));
    }

    @Test
    public void kingPromoTestForRed(){
        KingPromotion KP = new KingPromotion(game);
        Square KingSpace = board.getSquare(0, 1);
        assertTrue(KP.shouldBeKinged(KingSpace.getChecker()));
    }
}