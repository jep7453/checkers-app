package com.webcheckers.ui;


/**
 * Definning the enum types for the pieces of the game.
 * Initializing the pieces to fetch the values
 * It is for the single piece of the checker board.
 * **/
public class Piece {


    /**
     * Definning the type of the piece either it is a normal piece or special- king piece.
     * **/
    public enum Type{
        SINGLE, KING
    }

    /**
     * There is two type of checker piece- red or white color**/
    public enum Color{
        RED, WHITE
    }

    /***
     *
     */
    private final Type type;
    private final Color color;

    /**
     * Initializing the colors and the type.
     * @param color the color of the spaces
     * @param type  black or white**/
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    public Type getType() {

        return type;
    }

    public Color getColor() {

        return color;
    }
}
