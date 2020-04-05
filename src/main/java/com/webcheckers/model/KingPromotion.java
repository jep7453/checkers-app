package com.webcheckers.model;

public class KingPromotion {
    public Game game;

    public KingPromotion(Game game) {
        this.game = game;
    }

    public boolean shouldBeKinged(Checker checker){
        for(int i = 1; i < 8; i+=2){
            Square whiteGettingKingedSide = game.getBoard().getSquare(7,i-1);
            Square redGettingKingedSide = game.getBoard().getSquare(0,i);
            if(checker == whiteGettingKingedSide.getChecker() && checker.getColor() == Checker.Color.WHITE
            && checker.getType() != Checker.Type.KING){
                checker = new Checker(checker.getColor(), Checker.Type.KING);
                whiteGettingKingedSide.setChecker(checker);
                return true;
            }
            if(checker == redGettingKingedSide.getChecker() && checker.getColor() == Checker.Color.RED
            && checker.getType() != Checker.Type.KING){
                checker = new Checker(checker.getColor(), Checker.Type.KING);
                whiteGettingKingedSide.setChecker(checker);
                return true;
            }
        }
        return false;
    }
}