package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        KingSpace.setChecker(new Checker(Checker.Color.WHITE));
        assertTrue(KP.shouldBeKinged(KingSpace.getChecker()));
    }

    @Test
    public void kingPromoTestForRed(){
        KingPromotion KP = new KingPromotion(game);
        Square KingSpace = board.getSquare(0, 1);
        KingSpace.setChecker(new Checker(Checker.Color.RED));
        assertTrue(KP.shouldBeKinged(KingSpace.getChecker()));
    }
}