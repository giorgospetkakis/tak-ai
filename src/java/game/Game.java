package game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import beans.Board;
import beans.Player;

/**
 * Java bean that represents an instance of a Tak Game Session.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class Game implements BoardGame {
  
  public static final String TAK = "Tak";
  
  public static final String TIC_TAC_TOE = "Tic-Tac-Toe";
  
  public static final int NOT_STARTED = 0;

  public static final int IN_PROGRESS = 1;

  public static final int FINISHED = 2;
  
  public static int DEFAULT_BOARD_SIZE;
  
  private String gameType;

  private Board board;

  private ArrayList<Player> players;

  private int gameState;
  
  private Player current;

  private Player winner;

  private int score;
  
  private double timeElapsed;
  
  private int numTurns;
  
  private int hashCode;

  /**
   * Quick game constructor.
   * @param boardSize The size of the board (min 3, max 8)
   */
  public Game(int boardSize, String gameType) {
    this.setType(gameType);
    this.setBoard(new Board(boardSize));
    this.setPlayers(new ArrayList<Player>());

    this.addPlayer(new Player(1));
    this.addPlayer(new Player(1));

    this.setGameState(NOT_STARTED);
  }
  
  /**
   * Game constructor specifying players.
   * @param boardSize The size of the board (min 3, max 8)
   * @param p1 Player 1 of the game
   * @param p2 Player 2 of the game
   */
  public Game(int boardSize, Player p1, Player p2, String gameType) {
    this.setType(gameType);
    this.setBoard(new Board(boardSize));
    this.setPlayers(new ArrayList<Player>());

    this.addPlayer(p1);
    this.addPlayer(p2);

    this.setGameState(NOT_STARTED);
  }
  
  @Override
  public void start() {
    this.setTimeElapsed(System.currentTimeMillis());
    this.setHashCode(Date.from(Instant.now()).hashCode());
    this.setGameState(Game.IN_PROGRESS);
  }
  
  @Override
  public void end() {
    this.setTimeElapsed(System.currentTimeMillis() - this.getTimeElapsed());
    this.setGameState(Game.FINISHED);
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
   * Returns the winning player.
   * @return The winning player. Null if the game has no winner.
   */
  public Player getWinner() {
    return winner;
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

  /**
   * @return the hashCode
   */
  @Override
  public int hashCode() {
    return hashCode;
  }

  /**
   * @param hashCode the hashCode to set
   */
  public void setHashCode(int hashCode) {
    this.hashCode = hashCode;
  }

  /**
   * @return the gameType
   */
  public String getType() {
    return gameType;
  }

  /**
   * @param gameType the gameType to set
   */
  public void setType(String gameType) {
    this.gameType = gameType;
  }

  /**
   * @return the current
   */
  public Player whoseTurn() {
    return current;
  }

  /**
   * @param current the current to set
   */
  public void setCurrent(Player current) {
    this.current = current;
  }

  /**
   * @return the numTurns
   */
  public int getNumTurns() {
    return numTurns;
  }

  /**
   * @param numTurns the numTurns to set
   */
  public void setNumTurns(int numTurns) {
    this.numTurns = numTurns;
  }
  
  public Player getPlayer(int playerIndex) {
    return this.players.get(playerIndex);
  }
  
  public void addPlayer(Player player) {
    this.players.add(player);
  }
  
  public void incrementTurn() {
    this.numTurns++;
  }
}
