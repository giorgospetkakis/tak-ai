package io;

import beans.Game;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import org.apache.log4j.Logger;

/**
 * Enables writing game information to files.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class RecordsManager {

  private static String FILEPATH = "rec/test-records-file.csv";

  private static Logger logger = Logger.getLogger(RecordsManager.class);

  /**
   * Method to record a game to an external file.
   * 
   * @param game The game to be recorded
   */
  public static void record(Game game) {
    String gamecsv = getGameMetadata(game);

    try {
      logger.info("Attempting to record game: " + game.hashCode() + " to file " + FILEPATH);
      writeToFile(gamecsv, FILEPATH);
    } catch (FileNotFoundException fnfe) {
      logger.error("Could not find file " + FILEPATH);
    } catch (IOException ioe) {
      logger.error("Could not open file " + FILEPATH);
    }

    logger.info("Successfully recorded game: " + game.hashCode() + " to file " + FILEPATH);
  }

  /**
   * Records the game to an external file.
   * 
   * @param info The information to be recorded.
   */
  private static void writeToFile(String info, String filename) throws IOException {
    RandomAccessFile stream = new RandomAccessFile(filename, "rw");
    FileChannel channel = stream.getChannel();

    try {
      @SuppressWarnings("unused")
      FileLock lock = channel.tryLock();
    } catch (final OverlappingFileLockException e) {
      logger.error("Tried to write to file " + filename + " but it was locked.");
    } finally {
      stream.close();
      channel.close();
    }
    stream.writeChars(info);
  }

  /**
   * Returns a CSV format of metadata for the given game. The data is in the following order: 
   * Game hashcode 
   * Player 1 type | 0 dummy, 1 human, 2 ab, 3 rl 
   * Player 2 type | 0 dummy, 1 human, 2 ab, 3
   * rl Winning player (1 or 2) 
   * Game score 
   * Time elapsed since the start of the game
   * 
   * @param game The game to return metadata for
   * @return String CSV format of game metadata
   */
  private static String getGameMetadata(Game game) {
    String ret = "";
    ret += game.hashCode() + "," + game.getPlayers().get(0).getPlayerType() + ","
        + game.getPlayers().get(1).getPlayerType() + ","
        + (game.getWinner().compareTo(game.getPlayers().get(1)) + 1) + "," + game.getScore() + ","
        + game.getTimeElapsed();
    return ret;
  }
}
