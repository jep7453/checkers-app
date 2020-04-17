package com.webcheckers.application;

import com.webcheckers.model.Game;

import java.util.Comparator;

/**
 * Compares games based on rankings
 * @author Chris Tremblay
 */
class WinRateComparator implements Comparator {
    /**
     * Compares two games by their rankings
     * @param o1 the first game
     * @param o2 the second game
     * @return negative is 01 < 02, 0 if o1 = o2, else 1
     */
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Game && o2 instanceof Game)
            return ((Game) o1).compareTo(((Game) o2));
        return -1;
    }
}