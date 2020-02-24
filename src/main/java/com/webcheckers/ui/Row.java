package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *The row class implements the Iterator class.
 * It intialses the rows and spaces on the checkers board.**/
public class Row implements Iterable<Space> {

    private int index;
    private List<Space> spaces;

    public Row(int index) {
        this.index = index;
        spaces = new ArrayList<Space>();

        spaces.add(new Space(0,index));
        spaces.add(new Space(1,index));
        spaces.add(new Space(2,index));
        spaces.add(new Space(3,index));
        spaces.add(new Space(4,index));
        spaces.add(new Space(5,index));
        spaces.add(new Space(6,index));
        spaces.add(new Space(7,index));
    }

    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    public int getIndex() {
        return index;
    }
}