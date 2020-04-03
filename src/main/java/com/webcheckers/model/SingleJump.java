package com.webcheckers.model;

public class SingleJump {
    private Game game;

    public SingleJump(Game game) {
        this.game = game;
    }

    public boolean singleJumpWorked(int curRow,int curCol, int finRow, int finCol){
        Square currentPos = game.getBoard().getSquare(curRow,curCol);
        Square finalPos = game.getBoard().getSquare(finRow,finCol);

        if(currentPos == null || finalPos == null) return false;
        if(Math.abs(curRow-finRow) != Math.abs(curCol-finCol)) return false;
        if (!currentPos.hasChecker() || finalPos.hasChecker()) return false;

        Square middleSquarePiece = game.getBoard().getSquare(curRow+1,curCol+1);

        if(currentPos.getChecker().getType() == Checker.Type.KING){
            if(game.currentPlayer == game.getRedPlayer()){
                if(Math.abs(curRow-finRow) == 2 && Math.abs(curRow-finRow) == 2 && 
                    middleSquarePiece.getChecker().getColor() != currentPos.getChecker().getColor()){
                    return true;
                }
            }
        }

        //this is for non king
        if(game.currentPlayer == game.getRedPlayer()){
            if(finRow-curRow == 2 && finCol-curCol == 2 && 
                middleSquarePiece.getChecker().getColor() != currentPos.getChecker().getColor()){
                return true;
            }
        }
        

        return false;
    }
}