package com.webcheckers.model;

import com.webcheckers.Application;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;

import java.util.List;
/*
* This class is a skeleton for the replay game.
* @author Kesa Abbas Lnu <kl3468@rit.edu>*/
public class Replay {

    private Game completedGame;
    private Game replayGame;
    private int movesMade = 0;
    public Replay(GameCenter gameCenter, String gameID) {
        for(Game completedGame : gameCenter.getReplays()){
            if(completedGame.getGameID() == gameID){
                this.completedGame = completedGame;
                replayGame = new Game(completedGame.getRedPlayer(),completedGame.getWhitePlayer());
                return;
            }
        }
    }

    //checks if next move is there or not
    public boolean hasNextMove(){
        if(movesMade == completedGame.getMoves().size()) return false;
        return true;
    }

    //checks if any move is made yet or not
    public boolean hasPrevMove(){
        if(movesMade > 0) return true;
        return false;
    }

    //returns upcomming move
    private Move getNextMove(){
        if(hasNextMove()) return completedGame.getMoves().get(movesMade);
        return null;
    }

    //returns recent move
    private Move getPrevMove(){
        if(hasPrevMove()) return completedGame.getMoves().get(movesMade-1);
        return null;
    }

    //makes the next move
    public boolean makeNextTurn(){
        Move next = getNextMove();
        if(next != null){
            replayGame.makeMove(next);
        }
        return false;
    }

    //undo the last move
    public boolean makePrevTurn(){
        Move prev = getPrevMove();
        if(prev != null){
            replayGame.backUpMove();
            movesMade--;
            return true;
        }
        return false;
    }

    public Player getRedPlayer(){
        return completedGame.getRedPlayer();
    }

    public Player getWhitePlayer(){
        return completedGame.getWhitePlayer();
    }

    public boolean isRedPlayerTurn(){
        return replayGame.getCurrentPlayer() == replayGame.getRedPlayer();
    }

    public Game getReplayGame(){
        return replayGame;
    }

    public String getGameID(){
        return completedGame.getGameID();
    }

}
