package beans;

import java.util.ArrayList;
import game.GameManager;

public class Game {

  private Board board;

  private ArrayList<Player> players;

  private int gameState;

  private Player winner;

  private int score;
  
  private double timeElapsed;

  public static final int NOT_STARTED = 0;

  public static final int IN_PROGRESS = 1;

  public static final int FINISHED = 2;

  /**
   * Quick game constructor.
   * @param boardSize The size of the board (min 3, max 8)
   */
  public Game(int boardSize) {
    this.board = new Board(boardSize);
    this.players = new ArrayList<Player>();

    this.players.add(new Player());
    this.players.add(new Player());

    this.setGameState(NOT_STARTED);
  }
  
  /** TODO: Add coin-flip to decide play order
   * Game constructor specifying players.
   * @param boardSize The size of the board (min 3, max 8)
   * @param p1 Player 1 of the game
   * @param p2 Player 2 of the game
   */
  public Game(int boardSize, Player p1, Player p2) {
    this.board = new Board(boardSize);
    this.players = new ArrayList<Player>();

    this.players.add(p1);
    this.players.add(p2);

    this.setGameState(NOT_STARTED);
  }

  public void start() {

  }

  /**
   * Returns the winning player, if any.
   * 
   * @return The winning player. Null if no winner.
   */
  public Player getWinner() {
    return GameManager.detectWinner(this);
  }

  /**
   * Returns the current board.
   * 
   * @return the board The board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Sets the game board.
   * 
   * @param board the board to set
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  /**
   * @return the players
   */
  public ArrayList<Player> getPlayers() {
    return players;
  }

  /**
   * @param players the players to set
   */
  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

  /**
   * @return the gameState
   */
  public int getGameState() {
    return gameState;
  }

  /**
   * @param gameState the gameState to set
   */
  public void setGameState(int gameState) {
    this.gameState = gameState;
  }

  /**
   * @param winner the winner to set
   */
  public void setWinner(Player winner) {
    this.winner = winner;
  }

  /**
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * @param score the score to set
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * @return the timeElapsed
   */
  public double getTimeElapsed() {
    return timeElapsed;
  }

  /**
   * @param timeElapsed the timeElapsed to set
   */
  public void setTimeElapsed(double timeElapsed) {
    this.timeElapsed = timeElapsed;
  }
}
