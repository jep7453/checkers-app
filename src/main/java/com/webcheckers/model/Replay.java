package com.webcheckers.model;

import com.webcheckers.Application;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;

import java.util.ArrayList;
import java.util.List;
/*
* This class is a skeleton for the replay game.
* @author Kesa Abbas Lnu <kl3468@rit.edu>*/
public class Replay {

    private Game game;
    private List<Move> moves = new ArrayList<>();
    private int movesMade = 0;
    private Checkerboard board;
    private String title;
    public Replay(Game game) {
        this.game = game;
        this.moves = game.getReplay();
        this.board=new Checkerboard();
        this.title=game.getTitle();
    }

    //checks if next move is there or not
    public boolean hasNextMove(){
        if(movesMade == moves.size()) return false;
        return true;
    }

    //checks if any move is made yet or not
    public boolean hasPrevMove(){
        if(movesMade > 0) return true;
        return false;
    }

    public String getTitle() {
        return title;
    }

    //returns upcomming move
    private Move getNextMove(){
        if(hasNextMove()) return moves.get(movesMade);
        return null;
    }

    //returns recent move
    private Move getPrevMove(){
        if(hasPrevMove()) return moves.get(movesMade-1);
        return null;
    }

    //makes the next move
    public boolean makeNextTurn(){
        Move next = getNextMove();
        if(next != null){
            board.makeMove(next);
            movesMade++;
        }
        return false;
    }

    //undo the last move
    public boolean makePrevTurn(){
        Move prev = getPrevMove();
        if(prev != null){
            board.backUpMove(prev);
            movesMade--;
            return true;
        }
        return false;
    }

    public Player getRedPlayer(){
        return game.getRedPlayer();
    }

    public Player getWhitePlayer(){
        return game.getWhitePlayer();
    }

    public boolean isRedPlayerTurn(){
        return game.getCurrentPlayer() == game.getRedPlayer();
    }

    public Game getReplayGame(){
        return game;
    }

    public Checkerboard getBoard(){
        return board;
    }

    public String getGameID(){
        return game.getGameID();
    }

}
