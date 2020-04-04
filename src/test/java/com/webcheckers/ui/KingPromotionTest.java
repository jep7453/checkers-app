package com.webcheckers.ui;

import com.webcheckers.model.*;
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
    private Checkerboard checkerboard;

    @Test
    public void kingPromoTest(){
        game = KingPromotion.game;
        Square KingSpace = game.getBoard().getSquare(7,1);
        assertTrue(KingPromotion.shouldBeKinged(KingSpace.getChecker()));
    }
}