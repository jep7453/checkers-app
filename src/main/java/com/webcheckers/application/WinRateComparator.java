package com.webcheckers.application;

import com.webcheckers.model.Game;

import java.util.Comparator;

/**
 * Compares games based on rankings
 * @author Chris Tremblay
 */
class WinRateComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Game && o2 instanceof Game)
            return ((Game) o1).compareTo(((Game) o2));
        return -1;
    }
}