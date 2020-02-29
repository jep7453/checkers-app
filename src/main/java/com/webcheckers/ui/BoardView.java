package com.webcheckers.ui;

import com.webcheckers.model.Checkerboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*The following classes implement the UML for the board  data structures required for the game.
 * @author: Kesa Abbas Lnu <kl3468@rit.edu>
 */

/**
 * Implementing the Row and space classes to intialize the board for the checkerboard.
 */

public class BoardView  implements Iterable<Row> {

    /**
     * Creates the row for checkerboard, using spaces and cell index.
     */

    private List<Row> rows;

    /**
     * This method intializes the rows and new rows to the checker board.
     * */
    public BoardView(Checkerboard checkerboard) {
        rows = new ArrayList<Row>();
        rows.add(new Row(0,checkerboard));
        rows.add(new Row(1,checkerboard));
        rows.add(new Row(2,checkerboard));
        rows.add(new Row(3,checkerboard));
        rows.add(new Row(4,checkerboard));
        rows.add(new Row(5,checkerboard));
        rows.add(new Row(6,checkerboard));
        rows.add(new Row(7,checkerboard));

    }

    /**Implementing the iterator class to return the initailized the rows.
     *
     * @return rows intialized
     */
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}






