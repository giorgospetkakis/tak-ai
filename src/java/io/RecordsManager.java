package io;

import beans.Game;
import java.io.File;
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

  private static final String DIR = "rec/";
  
  private static final String FILE_PREFIX = "TakAIRecords-";
  
  private static final String FILE_SUFFIX = ".csv";

  private static final String HEADER = "key,p1,p2,winner,score,timeElapsed";
  
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
  
  private static final Logger logger = Logger.getLogger(RecordsManager.class);
  
  private static String CURRENT_RECORDS_FILE = "";
  
  static {
    int fileIdx = 0;
    do {
      CURRENT_RECORDS_FILE = DIR + FILE_PREFIX + fileIdx + FILE_SUFFIX;
      fileIdx++;
    } while (new File(DIR + FILE_PREFIX + fileIdx + FILE_SUFFIX).exists());
  }

  /**
   * Method to record a game to an external file.
   * 
   * @param game The game to be recorded
   */
  public static void record(Game game) {
    // Ensure write directory exists
    if (!new File(DIR).exists() | !new File(DIR).isDirectory()) {
      File directory = new File(DIR);
      directory.mkdirs();
      logger.error(DIR + " did not exist. Created new directory " + DIR);
    }
    
    if (new File(CURRENT_RECORDS_FILE).length() >= MAX_FILE_SIZE) {
      logger.info("Records file is too large. Creating new file...");
      createNewRecordsFile();
    }
    
    String gamecsv = getGameMetadata(game);
    
    try {
      logger.debug("Attempting to record game: " + game.hashCode() + " to file " + CURRENT_RECORDS_FILE);
      writeToFile(CURRENT_RECORDS_FILE, gamecsv);
      logger.info("Successfully recorded game: " + game.hashCode() + " to file " + CURRENT_RECORDS_FILE);
    } catch (FileNotFoundException fnfe) {
      logger.error("Could not find file " + CURRENT_RECORDS_FILE);
      createNewRecordsFile();
      record(game);
    } catch (IOException ioe) {
      logger.error("Could not open file " + CURRENT_RECORDS_FILE);
    }
  }

  /**
   * Records the game to an external file.
   * This method uses a FileLock system to not lose information
   * 
   * @param info The information to be recorded.
   */
  private static void writeToFile(String filename, String info) throws IOException {
    RandomAccessFile stream = new RandomAccessFile(filename, "rw");
    stream.seek(stream.length());
    FileChannel channel = stream.getChannel();

    try {
      @SuppressWarnings("unused")
      FileLock lock = channel.tryLock();
      stream.writeChars(info);
    } catch (final OverlappingFileLockException e) {
      writeToFile(filename + ".temp", info);
      logger.error("Tried to write to file " + filename + " but it was locked.");
    } finally {
      stream.close();
      channel.close();
    }
  }
  
  /**
   * Creates a new file in the records folder.
   * @throws IOException Throws an exception if file creation fails
   */
  public static void createNewRecordsFile() {
    int fileIdx = new File(DIR).listFiles().length;
    String filename = DIR + FILE_PREFIX + fileIdx + FILE_SUFFIX;
    
    while (new File(filename).exists()) {
      filename = DIR + FILE_PREFIX + ++fileIdx + FILE_SUFFIX;
    }
    
    try {
      File file  = new File(filename);
      if (file.createNewFile()) {
        logger.info("Created new records file: " + filename);
        CURRENT_RECORDS_FILE = filename;
        writeToFile(CURRENT_RECORDS_FILE, HEADER);
      }
    } catch (IOException ioe) {
      logger.error("Could not create file " + filename);
    }
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
    String ret = "\n";
    ret += game.hashCode() + "," + game.getPlayers().get(0).getPlayerType() + ","
        + game.getPlayers().get(1).getPlayerType() + ","
        + (game.getWinner().compareTo(game.getPlayers().get(1)) + 1) + "," + game.getScore() + ","
        + game.getTimeElapsed();
    return ret;
  }
}
