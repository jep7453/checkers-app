package com.webcheckers.ui;

import com.webcheckers.model.Checker;
import com.webcheckers.model.Checkerboard;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/***
 * This is unit test for BoardView class of UI Tier.
 * @author Kesa Abbas Lnu <kl3468rit.edu>
 */

@Tag("UI-tier")
class BoardViewTest {

    @Test
    void ctor_nullArg(){
        assertThrows(NullPointerException.class,()-> new BoardView( null),"BoardView constructed without cherkerboard");
    }

    @Test
    void ctor_withArg(){
        final Checkerboard checkerboard = new Checkerboard();
        final BoardView boardView = new BoardView(checkerboard);

        //check for not null rows list
        assertNotNull(boardView.getRows(),"BoardView has rows defined");

        //check for exactly 8 rows
        assertEquals(8,boardView.getRows().size(),"BoardView have 8 rows");

        //for each row
        for(int i = 0; i < 8 ; i++){

            final Row ithRow = boardView.getRows().get(i);
            //check for not null row in row list at ith index
            assertNotNull(ithRow,"BoardView have " + i + "th row defined");

            //check for not null spaces list for ith index row
            assertNotNull(ithRow.getSpaces(),i + "th row have spaces defined");

            //check for exctly 8 spaces for space list in ith index row
            assertEquals(8,ithRow.getSpaces().size(),"BoardView have 8 spaces in the " +  i + "th row");

            //check index of the row
            assertEquals(i,ithRow.getIndex(),"Row index match its position in BoardView");

            //for each space
            for(int j = 0; j < 8 ; j++){

                final Space jthSpace = ithRow.getSpaces().get(j);
                //check for not null space in space list
                assertNotNull(jthSpace,"BoardView have " + j + "th space in the " + i + "th row defined");

                //check cellIndex of the space
                assertEquals(j,jthSpace.getCellIdx(),"Space cellIndex match its position in BoardView");

                //check rowIndex of the space
                assertEquals(i,jthSpace.getRowIdx(),"Space rowIndex match its row position in BoardView");

                //only for row 0,1,6 and 7
                if((i + j) % 2 == 1  && i<3 || (i + j) % 2 == 1  && i > 4){

                    //check not null piece in space for row 0,1,6 and 7
                    assertNotNull(jthSpace.getPiece(),"BoardView have piece at " + j + "th space in the " + i + "th row defined");

                    //check for Type SINGLE piece
                    assertEquals(Piece.Type.SINGLE,jthSpace.getPiece().getType(),"BoardView have SINGLE piece at " + i + "th row " + j + "th column at the start of the game");

                    //check for Color of piece according to checkerboard
                    assertEquals(checkerboard.getSquare(i,j).getChecker().getColor() == Checker.Color.RED ? Piece.Color.RED : Piece.Color.WHITE,jthSpace.getPiece().getColor(), "Piece color at " + i + "th row and " + j + "th column is correct");
                }
            }
        }
    }
}