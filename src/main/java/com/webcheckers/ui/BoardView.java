package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* The following classes implement the UML for the board  data structures required for the game.
 * @author: Kesa Abbas Lnu
 * */

/**
 * Implementing the Row and space classes to intialize the board view.
 */

public class BoardView implements Iterable<Row>{

        private List<Row> rows;

        //Initializing the board's view to contain the seven rows and it utilizes the arrays to do it.

        public BoardView() {
            rows = new ArrayList<Row>();
            rows.add(new Row(0));
            rows.add(new Row(1));
            rows.add(new Row(2));
            rows.add(new Row(3));
            rows.add(new Row(4));
            rows.add(new Row(5));
            rows.add(new Row(6));
            rows.add(new Row(7));
        }

    /**Implementing the iterator class to return the initailized the rows.
     *
     * @return rows intialized
     */
    public Iterator<Row> iterator() {
            return rows.iterator();
        }
    }






