package com.webcheckers.model;

/** It demonstrates the single move of checker piece across the board
 *
 * @author Kesa Abbas Lnu <kl3468@rit.edu>
 */
public class SimpleMove {

    private Game game;
    private Player winner;

    public SimpleMove(Game game) {
        this.game = game;
        winner = null;
}
    //returns true if move is made and updates the checkers position in the checkerboard.
    //if game is won then winner is updated.
    //returns false if move cannot be made or the game is already over
    public boolean moveChecker(int rowS,int colS, int rowD, int colD){
        //check if game is already over
        if(winner != null) return false;

        Square start = game.getBoard().getSquare(rowS,colS);
        Square dest = game.getBoard().getSquare(rowD,colD);

        //check for non null
        if(start == null || dest == null) return false;

        //check if start and dest are diagonally located
        if(Math.abs(rowS-rowD) != Math.abs(colS-colD)) return false;

        //check if start has checkera and dest is vacant
        if(start.getChecker() != null && dest.getChecker() == null){

            //check if checker color is RED and current player is RED
            if(game.currentPlayer == game.getRedPlayer() && start.getChecker().getColor() == Checker.Color.RED){

                //checker is SINGLE
                if(start.getChecker().getType() == Checker.Type.SINGLE){

                    //check if dest is away from player
                    if(rowS < rowD){

                        //check if we are jumping over one square
                        if(rowD - rowS == 2){
                            //get the square jumped
                            Square squareJumped = game.getBoard().getSquare((rowS+rowD)/2,(colS+colD)/2);

                            //square jumped must have checker of other player
                            if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == Checker.Color.WHITE){

                                //Eliminate the checker being jumped
                                squareJumped.setChecker(null);

                                //Remove checker from start square and put it at dest square
                                dest.setChecker(start.getChecker());
                                start.setChecker(null);

                                //has game won
                                if(hasGameWon(Checker.Color.RED)){
                                    winner = game.currentPlayer;
                                    return true;
                                }

                                //current player remains the current player because he gets chance to move again
                                return true;
                            }
                        }
                        //check if we are not jumping over squares{
                        else if(rowD - rowS == 1){

                            //Remove checker from start square and put it at dest square
                            dest.setChecker(start.getChecker());
                            start.setChecker(null);

                            //has game won
                            if(hasGameWon(Checker.Color.RED)){
                                winner = game.currentPlayer;
                                return true;
                            }

                            //turn of other player(WHITE)
                            game.currentPlayer = game.getWhitePlayer();
                            return true;
                        }else return false;

                    }else return false;
                }
                //checker is KING
                else {

                    //check if we are jumping over one square
                    if(Math.abs(rowD - rowS) == 2){
                        //get the square jumped
                        Square squareJumped = game.getBoard().getSquare((rowS+rowD)/2,(colS+colD)/2);

                        //square jumped must have checker of other player
                        if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == Checker.Color.WHITE){

                            //Eliminate the checker being jumped
                            squareJumped.setChecker(null);

                            //Remove checker from start square and put it at dest square
                            dest.setChecker(start.getChecker());
                            start.setChecker(null);

                            //has game won
                            if(hasGameWon(Checker.Color.RED)){
                                winner = game.currentPlayer;
                                return true;
                            }

                            //current player remains the current player because he gets chance to move again
                            return true;
                        }
                    }
                    //check if we are not jumping over squares{
                    else if(Math.abs(rowD - rowS) == 1){

                        //Remove checker from start square and put it at dest square
                        dest.setChecker(start.getChecker());
                        start.setChecker(null);

                        //has game won
                        if(hasGameWon(Checker.Color.RED)){
                            winner = game.currentPlayer;
                            return true;
                        }

                        //turn of other player(WHITE)
                        game.currentPlayer = game.getWhitePlayer();
                        return true;
                    }else return false;

                }
            }
            //check if checker color is WHITE and current player is WHITE
            else if(game.currentPlayer == game.getWhitePlayer() && start.getChecker().getColor() == Checker.Color.WHITE){

                //checker is SINGLE
                if(start.getChecker().getType() == Checker.Type.SINGLE){

                    //check if dest is away from player
                    if(rowS > rowD){

                        //check if we are jumping over one square
                        if(rowS - rowD == 2){
                            //get the square jumped
                            Square squareJumped = game.getBoard().getSquare((rowS+rowD)/2,(colS+colD)/2);

                            //square jumped must have checker of other player
                            if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == Checker.Color.RED){

                                //Eliminate the checker being jumped
                                squareJumped.setChecker(null);

                                //Remove checker from start square and put it at dest square
                                dest.setChecker(start.getChecker());
                                start.setChecker(null);

                                //has game won
                                if(hasGameWon(Checker.Color.RED)){
                                    winner = game.currentPlayer;
                                    return true;
                                }

                                //current player remains the current player because he gets chance to move again
                                return true;
                            }
                        }
                        //check if we are not jumping over squares{
                        else if(rowS - rowD == 1){

                            //Remove checker from start square and put it at dest square
                            dest.setChecker(start.getChecker());
                            start.setChecker(null);

                            //has game won
                            if(hasGameWon(Checker.Color.RED)){
                                winner = game.currentPlayer;
                                return true;
                            }

                            //turn of other player(RED)
                            game.currentPlayer = game.getRedPlayer();
                            return true;
                        }else return false;

                    }else return false;
                }
                //checker is KING
                else {

                    //check if we are jumping over one square
                    if(Math.abs(rowS - rowD) == 2){
                        //get the square jumped
                        Square squareJumped = game.getBoard().getSquare((rowS+rowD)/2,(colS+colD)/2);

                        //square jumped must have checker of other player
                        if(squareJumped != null && squareJumped.getChecker()!= null && squareJumped.getChecker().getColor() == Checker.Color.RED){

                            //Eliminate the checker being jumped
                            squareJumped.setChecker(null);

                            //Remove checker from start square and put it at dest square
                            dest.setChecker(start.getChecker());
                            start.setChecker(null);

                            //has game won
                            if(hasGameWon(Checker.Color.RED)){
                                winner = game.currentPlayer;
                                return true;
                            }

                            //current player remains the current player because he gets chance to move again
                            return true;
                        }
                    }
                    //check if we are not jumping over squares{
                    else if(Math.abs(rowS - rowD) == 1){

                        //Remove checker from start square and put it at dest square
                        dest.setChecker(start.getChecker());
                        start.setChecker(null);

                        //has game won
                        if(hasGameWon(Checker.Color.RED)){
                            winner = game.currentPlayer;
                            return true;
                        }

                        //turn of other player(RED)
                        game.currentPlayer = game.getRedPlayer();
                        return true;
                    }else return false;

                }
            }
        }
        return false;
    }

    private boolean hasGameWon(Checker.Color winingTeam){

        //check for other player's checker on the board
        for(int r = 0 ; r < 8 ; r++){
            for(int c = 0 ; c < 8 ; c++){
                Checker checker = game.getBoard().getSquare(r,c).getChecker();
                if(checker != null && checker.getColor() != winingTeam) return false;
            }
        }
        return true;
    }

    public Player winner(){
        return winner;
    }
}

