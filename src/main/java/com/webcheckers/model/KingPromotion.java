package com.webcheckers.model;

public class KingPromotion {
    public static Game game;

    public KingPromotion(Game game) {
        this.game = game;
    }

    public static boolean shouldBeKinged(Checker checker){
        for(int i = 0; i < 8; i++){
            Square redSide = game.getBoard().getSquare(7,i);
            Square whiteSide = game.getBoard().getSquare(0,i);
            if(redSide.hasChecker() && redSide.getChecker().getType() != Checker.Type.KING){
                Checker kingPiece = new Checker(redSide.getChecker().getColor(), Checker.Type.KING);
                redSide.setChecker(kingPiece);
                return true;
            }
            if(whiteSide.hasChecker() && whiteSide.getChecker().getType() != Checker.Type.KING){
                Checker kingPiece = new Checker(whiteSide.getChecker().getColor(), Checker.Type.KING);
                whiteSide.setChecker(kingPiece);
                return true;
            }
        }
        return false;
    }
}