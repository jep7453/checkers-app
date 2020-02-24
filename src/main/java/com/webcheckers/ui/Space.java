package com.webcheckers.ui;
/**
 * This class intialises the an individua space on the checker board.
 * And returns the position to the main board.
 * It checks for the valid cell on the board and if its invalid it doesn twork**/
public class Space {

    private int cellIdx;
    private Piece piece;
    private int rowIdx;

    /**
     *Intialising the values for the Cell and Row Index
     */

    public Space(int cellIdx, int rowIdx) {
        this.cellIdx = cellIdx;
        this.rowIdx = rowIdx;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    /*Checking to see if the specific row and coulumn does exist
     */
    public boolean isValid(){
        if((rowIdx + cellIdx) % 2 == 0 && piece == null) return true;
        return false;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
