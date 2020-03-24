package com.webcheckers.ui;

import com.webcheckers.model.Checkerboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *The row class implements the Iterator class.
 * It intialses the rows and spaces on the checkers board.
 * author : Kesa Abbas Lnu**/
public class Row implements Iterable<Space> {

    private int index;
    private List<Space> spaces;


    /**
     * Definning the constructor which intialises the a space on the board with the cell index.
     * This method creates the object **/
    public Row(int index, Checkerboard checkerboard) {
        this.index = index;
        spaces = new ArrayList<Space>();

        spaces.add(new Space(0,index,checkerboard));
        spaces.add(new Space(1,index,checkerboard));
        spaces.add(new Space(2,index,checkerboard));
        spaces.add(new Space(3,index,checkerboard));
        spaces.add(new Space(4,index,checkerboard));
        spaces.add(new Space(5,index,checkerboard));
        spaces.add(new Space(6,index,checkerboard));
        spaces.add(new Space(7,index,checkerboard));
    }

    public Iterator<Space> iterator() {

        return spaces.iterator();
    }

    /**
     * Getting the index for intializing the rows.**/
    public int getIndex() {

        return index;
    }

    public List<Space> getSpaces() {
        return spaces;
    }
}