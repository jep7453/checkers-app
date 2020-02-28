package com.webcheckers.ui;


/**
 * Definning the enum types for the pieces of the game.
 * Initializing the pieces to fetch the values **/
public class Piece {

    //Definning the Enumerators for the Colors and the type of the checker pieces.

    public enum Type{
        SINGLE, KING
    }

    public enum Color{
        RED, WHITE
    }

    // Definning the types for the data structures.
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
