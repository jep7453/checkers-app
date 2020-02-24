package com.webcheckers.ui;


/**
 * Definning the enum types for the pieces of the game.
 * Initializing the pieces to fetch the values **/
public class Piece {


    public enum Type{
        SINGLE, KING
    }

    public enum Color{
        RED, WHITE
    }

    private final Type type;
    private final Color color;

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
