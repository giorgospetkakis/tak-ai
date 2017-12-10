package game;

import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import beans.Board;
import beans.Cell;
import beans.Move;
import beans.MovementMove;
import beans.Player;
import players.DummyPlayer;

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

  private LinkedList<Move> moves;

  private int gameState;

  private Player current;

  private Player winner;

  private int score;

  private double timeElapsed;

  private int numTurns;

  private int hashCode;
  
  private static final Logger logger = Logger.getLogger(Game.class);

  /**
   * Quick game constructor.
   * 
   * @param boardSize The size of the board (min 3, max 8)
   */
  public Game(int boardSize, String gameType) {
    this.setType(gameType);
    this.setBoard(new Board(boardSize));
    this.setPlayers(new ArrayList<Player>());
    this.setMoves(new LinkedList<Move>());

    this.addPlayer(new DummyPlayer());
    this.addPlayer(new DummyPlayer());

    this.setGameState(NOT_STARTED);
  }

  /**
   * Game constructor specifying players.
   * 
   * @param boardSize The size of the board (min 3, max 8)
   * @param p1 Player 1 of the game
   * @param p2 Player 2 of the game
   */
  public Game(int boardSize, Player p1, Player p2, String gameType) {
    this.setType(gameType);
    this.setBoard(new Board(boardSize));
    this.setPlayers(new ArrayList<Player>());
    this.setMoves(new LinkedList<Move>());

    this.addPlayer(p1);
    this.addPlayer(p2);

    this.setGameState(NOT_STARTED);
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
   * 
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
   * Changes the current player to the other one.
   */
  public void swapCurrentPlayer() {
    this.current = this.whoseTurn() == this.getPlayer(0) ? this.getPlayer(1) : this.getPlayer(0);
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
  
  /**
   * Counts the number of cells controlled by player <code>p</code>.
   * 
   * @param p the player
   * @return the number of cells controlled by player <code>p</code>.
   */
  public int getCellsControlled(Player p) {
    int count = 0;
    
    for(int i = 0; i < this.getBoard().getSize(); i++) {
      for(int j = 0; j < this.getBoard().getSize(); j++) {
        Player cur = BoardManager.getCell(this.getBoard(), i, j).getOwner();
        if(cur != null && cur.equals(p))
          count++;
      }
    }
    
    return count;
  }

  /**
   * Counts the number of cells controlled by player <code>p</code> using standing stones.
   * 
   * @param p the player
   * @return the number of cells controlled by player <code>p</code> using standing stones.
   */
  public int getCellsControlledStanding(Player p) {
    int count = 0;
    
    for(int i = 0; i < this.getBoard().getSize(); i++) {
      for(int j = 0; j < this.getBoard().getSize(); j++) {
        Cell cur = BoardManager.getCell(this.getBoard(), i, j);
        if(cur.getOwner() != null && cur.getOwner().equals(p) && cur.top().isStandingStone())
          count++;
      }
    }
    
    return count;
  }
  

  public void undoMove(Move move) {
    makeMove(move.getInverse());
    if (this.getGameState() == Game.IN_PROGRESS) {
      this.moves.removeLast();
      this.moves.removeLast();
    }
  }

  public void makeMove(Move move) {
    if (this.getGameState() == Game.IN_PROGRESS && !(move instanceof MovementMove)) {
      this.moves.addLast(move);
    }
  }

  /**
   * @return the moves
   */
  public LinkedList<Move> getMoves() {
    return moves;
  }

  /**
   * @param moves the moves to set
   */
  public void setMoves(LinkedList<Move> moves) {
    this.moves = moves;
  }
}
