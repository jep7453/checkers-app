package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

/**
 * Test suite for no moves
 */
@Tag("model")
public class NoMovesTest {

    /**
     * The checkerboard
     */
    private Checkerboard checkerboard;

    /** The rank used for testing */
    private final int RANK = 3;

    /** the file used for testing */
    private final int FILE = 4;

    /**
     * Test no moves for a single piece
     */
    @Test
    public void cantMoveSingle(){
        checkerboard = new Checkerboard("noMovesSingle.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Test a simple move left
     */
    @Test
    public void simpleMoveLeft(){
        checkerboard = new Checkerboard("simpleMoveLeft.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK, FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(3,0)));
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(3,0)));

    }

    /**
     * Test a simple move right
     */
    @Test
    public void simpleMoveRight(){
        checkerboard = new Checkerboard("simpleMoveRight.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK, FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Test a single jump move left
     */
    @Test
    public void singleJumpLeft(){
        checkerboard = new Checkerboard("singleJumpLeft.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK, FILE)));
        assertTrue(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.isValidMove(new Move(new Position(2,5),new Position(4,3))).equals(Move.Type.JUMP));
    }

    /**
     * Test a single jump move left
     */
    @Test
    public void singleJumpRight(){
        checkerboard = new Checkerboard("singleJumpRight.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure you cannot jump the same color
     */
    @Test
    public void jumpSameColorLeft(){
        checkerboard = new Checkerboard("jumpSameColorLeft.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK, FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure you cannot jump the same color
     */
    @Test
    public void jumpSameColorRight(){
        checkerboard = new Checkerboard("jumpSameColorRight.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure you can jump a different color
     */
    @Test
    public void jumpDiffColorRight(){
        checkerboard = new Checkerboard("jumpDiffColorRight.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure you can jump a different color
     */
    @Test
    public void jumpDiffColorLeft(){
        checkerboard = new Checkerboard("jumpDiffColorLeft.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Check that the king cannot move
     */
    @Test
    public void noMovesking(){
        checkerboard = new Checkerboard("noMovesKing.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Test the king can move backwards left
     */
    @Test
    public void simpleMoveKingLeft(){
        checkerboard = new Checkerboard("simpleMoveKingLeft.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * king can move backwards right
     */
    @Test
    public void simpleMoveKingRight(){
        checkerboard = new Checkerboard("simpleMoveKingRight.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * test the king cannot jump the same color
     */
    @Test
    public void jumpSameColorLeftKing(){
        checkerboard = new Checkerboard("jumpSameColorLeftKing.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * test the king cannot jump the same color
     */
    @Test
    public void jumpSameColorRightKing(){
        checkerboard = new Checkerboard("jumpSameColorRightKing.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure king can jump a different color
     */
    @Test
    public void jumpDiffColorRightKing(){
        checkerboard = new Checkerboard("jumpDiffColorRightKing.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Make sure king can jump a different color
     */
    @Test
    public void jumpDiffColorLeftKing(){
        checkerboard = new Checkerboard("jumpDiffColorLeftKing.txt");
        assertTrue(checkerboard.pieceCanMove(checkerboard.getSquare(RANK,FILE)));
        assertTrue(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * test that it can't move when in the corner at an edge
     */
    @Test
    public void cantMoveEdge(){checkerboard = new Checkerboard("edgeMove.txt");
        assertFalse(checkerboard.pieceCanMove(checkerboard.getSquare(0,1)));
        assertFalse(checkerboard.pieceCanJump(checkerboard.getSquare(RANK,FILE)));
    }

    /**
     * Check that no moves returns false with no pieces
     */
    @Test
    public void noRedPieces(){

        Game game = new Game(new Player("hajs"), new Player("ahdsf"));
        Checkerboard checkerboard = new Checkerboard("noRedPieces.txt");
        game.setBoard(checkerboard);

        assertFalse( game.playerCanMove(game.getRedPlayer()));
    }


    /**
     * Test that no moves works
     */
    @Test
    public void noMovesRed(){
        Game game = new Game(new Player("hajs"), new Player("ahdsf"));
        Checkerboard checkerboard = new Checkerboard("noMovesRed.txt");
        game.setBoard(checkerboard);
        assertFalse( game.playerCanMove(game.getRedPlayer()));
    }

    /**
     * Test that no moves works
     */
    @Test
    public void noMovesRedKing(){
        Game game = new Game(new Player("hajs"), new Player("ahdsf"));
        Checkerboard checkerboard = new Checkerboard("noMovesRedKing.txt");
        game.setBoard(checkerboard);

        assertFalse( game.playerCanMove(game.getRedPlayer()));
    }

    public static void printBoard(Checkerboard board) {
        Square square;
        String col;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                square = board.getSquare(i, j);
                if(square.hasChecker())
                    if(square.getChecker().getColor() == Checker.Color.RED)
                        col = "R";
                    else
                        col = "W";
                else
                    col = " ";

                System.out.print(String.format("| %s ", col));

            }
            System.out.println("|");
        }
    }
}
