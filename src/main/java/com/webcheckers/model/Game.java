package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game implements Comparable {

  /** Represents a checker Game.
   *
   * @author Scott Court <sxc4981@rit.edu>
   */

  private Player redPlayer;     /** The RED Player of this game. */
  private Player whitePlayer;   /** The WHITE Player of this game. */
  private Checkerboard board;   /** The Checkerboard this game is played on. */
  private List<Move> moves = new ArrayList<>(); /** A list of moves made, to backup */
  private List<Move> replay = new ArrayList<>(); /** A list of moves, to replay the game. */
  private final String gameID;
  private String title;
  private boolean turnSubmitted;
  private int numSpectators;
  private ArrayList<Player> spectators;

  Player currentPlayer;         /** The Player whose turn it currently is. */

  private Player winner;
  private boolean resigned;

  /** Creates a new Game object with the specified players.
   * @param redPlayer   The Player to play as red.
   * @param whitePlayer The Player to play as white.
   */
  public Game(Player redPlayer, Player whitePlayer) {
    this.gameID = UUID.randomUUID().toString();
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.spectators = new ArrayList<>();
    this.numSpectators = 0;
    this.board = new Checkerboard();
    this.currentPlayer = redPlayer;
    this.resigned=false;
    this.title = this.redPlayer.getName() + " & " +this.whitePlayer.getName();
    this.turnSubmitted=false;
  }

  /** Gets the Player whose turn it currently is.
   * @return The Player whose turn it currently is.
   */
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  /** Gets the Player playing as red.
   * @return The Player playing as red.
   */
  public Player getRedPlayer() {
    return this.redPlayer;
  }

  /** Gets the Player playing as white.
   * @return The Player playing as white.
   */
  public Player getWhitePlayer() {
    return this.whitePlayer;
  }

  /** Gets the Checkerboard this Game is being played on.
   * @return The Checkerboard this game is being played on.
   */
  public Checkerboard getBoard() {
    return this.board;
  }

  /**
   * Get the unique game identifier
   * @return the game id
   */
  public String getGameID(){
    return (gameID);
  }

  /**
   * Get the title
   * @return the title
   */
  public String getTitle(){
    return (title);
  }


  public List<Move> getReplay(){
    return (replay);
  }



  public Move.Type isValidMove(Move move) {
    return board.isValidMove(move);

  }

  public void makeMove(Move move) {
    board.makeMove(move);
    moves.add(move);
  }

  /**
   * Tell a game a player started spectating
   * @param player the new spectator
   */
  public synchronized void startedSpectating(Player player){
    spectators.add(player);
  }

  /**
   * Tell the game a spectator left
   * @param player the spectator leaving
   */
  public synchronized void stoppedSpectating(Player player){
    spectators.remove(player);
  }

  /**
   * Get the total number of spectators
   * @return the total number of spectators
   */
  public synchronized int getNumSpectators(){
    return this.spectators.size();
  }

  public Player currentPlayer() {
    return currentPlayer;
  }

  public void backUpMove() {
    board.backUpMove(moves.get(moves.size()-1));
    moves.remove(moves.size()-1);
  }

  public void switchPlayer() {
    for(Move move :moves) {
      replay.add(move);
    }
    moves.clear();
    if(currentPlayer.equals(whitePlayer)) {
      currentPlayer=redPlayer;
    }
    else {
      currentPlayer=whitePlayer;
    }
  }

  /**
   * Setter for checkerbaord
   *
   * @param board
   */
  public void setBoard(Checkerboard board) {
    this.board = board;
  }


  public boolean isValidTurn() {

    Move lastMove = moves.get(moves.size()-1);
    if(moves.get(0).getType()==Move.Type.JUMP) {
      return jumpValidation(lastMove);
    }
    else {
      return singleValidation(lastMove);
    }
  }

  public boolean jumpValidation(Move lastMove) {
    // Checks if a simple move is made in the same turn as a jump move
      for (Move move : moves) {
        if (move.getType().equals(Move.Type.SINGLE)) {
          System.out.println("cant do dat");
          return false;
        }
      }
    // Checks to make sure you make a multiple move if you can

      Square lastSquare = board.getSquare(lastMove.getEnd().getRow(),lastMove.getEnd().getCell());
      Checkerboard newBoard = board;
      if(currentPlayer.equals(redPlayer)) {

        newBoard = board.reverseBoard();
        for (int i = 0; i < Checkerboard.NUM_RANKS; i++) {
          for (int j = 0; j < Checkerboard.NUM_FILES; j++) {
            if (newBoard.getSquare(i, j).hasChecker()) {
              if (newBoard.getSquare(i, j).getChecker().equals(lastSquare.getChecker())) {
                lastSquare = newBoard.getSquare(i, j);
              }
            }
          }
        }

      }
      if(newBoard.pieceCanJump(lastSquare)) {
        System.out.println("Need to jump twice");
        return false;
      }
      turnSubmitted=true;
      return true;
  }

  public static void printBoard(Checkerboard board) {
    Square square;
    String col;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        square = board.getSquare(i, j);
        if(square.hasChecker())
          if(square.getChecker().getColor() == Checker.Color.RED)
            col = "R";
          else
            col = "W";
        else
          col = " ";

        System.out.print(String.format("| %s (%d,%d) ", col, i, j));

      }
      System.out.println("|");
    }
    System.out.println();
  }

  public boolean singleValidation(Move lastMove) {
    //Checks if multiple simple moves are made
    if (moves.size() > 1) {
      return false;
    }
    // Checks that a jump wasn't a possible move
    Move reverseMove = new Move(lastMove.getEnd(),lastMove.getStart());
    backUpMove();
    if(playerCanJump(currentPlayer)) {
      System.out.println("Need to jump");
      makeMove(lastMove);
      return false;
    }
    makeMove(lastMove);
    turnSubmitted=true;
    return true;



  }

  /**
   * Check if a player can jump
   * @param player the player to check for
   * @return true if the can false if no
   */
  public boolean playerCanJump(Player player) {
    // assume player can move
    boolean canMove = false;
    Square tempSquare;
    Checkerboard checkerboard = getBoard();

    // player is white player
    if (player.equals(whitePlayer)) {
      if (checkerboard.allPiecesCaptured(Checker.Color.WHITE)) {
        return (false);
      }

      // iterate through board
      for (int i = 0; i < Checkerboard.NUM_RANKS; i++) {
        for (int j = 0; j < Checkerboard.NUM_FILES; j++) {
          // get square
          tempSquare = checkerboard.getSquare(i, j);
          // if the squares exists and has a white checker
          if (tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.WHITE) {
            canMove |= checkerboard.pieceCanJump(tempSquare);
          }
        }
      }
    }
    // else the red player so flip the board
    else{
      if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
        return (false);
      }
      Checkerboard flippedBoard = checkerboard.reverseBoard();

      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = flippedBoard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            canMove |= flippedBoard.pieceCanJump(tempSquare);
          }
        }
      }
      flippedBoard = null;
    }

    return (canMove);
  }

    /**
     * Check if a player can move
     * @param player the player to check for
     * @return true if the can false if no
     */
    public boolean playerCanMove(Player player){
      // assume player can move
      boolean canMove = false;
      Square tempSquare;
      Checkerboard checkerboard = getBoard();

      // player is white player
      if(player.equals(whitePlayer)){
        if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
          return (false);
        }

        // iterate through board
        for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
          for(int j = 0; j < Checkerboard.NUM_FILES; j++){
            // get square
            tempSquare = checkerboard.getSquare(i,j);
            // if the squares exists and has a white checker
            if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.WHITE){
              canMove |= checkerboard.pieceCanMove(tempSquare);
            }
          }
        }
      }

    // else the red player so flip the board
    else{
      if(checkerboard.allPiecesCaptured(Checker.Color.WHITE)){
        return (false);
      }
      Checkerboard flippedBoard = checkerboard.reverseBoard();
      // iterate through board
      for(int i = 0; i < Checkerboard.NUM_RANKS; i++){
        for(int j = 0; j < Checkerboard.NUM_FILES; j++){
          // get square
          tempSquare = flippedBoard.getSquare(i,j);
          // if the squares exists and has a white checker
          if(tempSquare.hasChecker() && tempSquare.getChecker().getColor() == Checker.Color.RED){
            canMove |= flippedBoard.pieceCanMove(tempSquare);
          }
        }
      }
      flippedBoard = null;
    }

    return (canMove);
  }

  public void resigned() {
      resigned = true;
      if(currentPlayer==whitePlayer) {
        winner=redPlayer;
      }
    if(currentPlayer==redPlayer) {
      winner = whitePlayer;
    }
  }

  /**
   * Check if the game has been won by pieces captured
   * or no moves
   * @return true if won, false if not
   */
  public boolean isGameWon(){
    boolean won = false;
    if(resigned) {
      won = true;
    }
    if(board.allPiecesCaptured(Checker.Color.RED)) {
      winner = whitePlayer;
      won = true;
    }
    if(board.allPiecesCaptured(Checker.Color.WHITE)) {
      winner = redPlayer;
      won = true;
    }
    if(!playerCanMove(redPlayer)) {
      winner = whitePlayer;
      won = true;
    }
    if(!playerCanMove(whitePlayer)) {
      winner = redPlayer;
      won = true;
    }
    return (won);
  }
//getting the list of moves
public List<Move> getMoves() {
  return moves;
}

  public void replaySetPlayer(Checker.Color color) {
    if(color== Checker.Color.RED) {
      currentPlayer=whitePlayer;
    }
    else {
      currentPlayer=redPlayer;
    }
  }


  /**
   * Gets the player who won
   * @return the player who won
   */
  public Player getWinner(){
    return (this.winner);
  }

  /**
   * Gets the ranking of the game or the average
   * if the players ranks
   *
   * @return the average of the players ranks
   */
  public int getGameRank(){
    return (int)(0.5 * (double)(this.redPlayer.getWinRate() + this.whitePlayer.getWinRate()));
  }

  @Override
  public int compareTo(Object o) {
    return Integer.compare(this.getGameRank(), ((Game) o).getGameRank());
  }
}

