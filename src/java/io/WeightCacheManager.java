package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import org.apache.log4j.Logger;
import game.Game;
import players.ab.AlphaBetaAI;
import players.rl.ValueApproximator;
import run.GameManager;

/**
 * Enables writing game information to files.
 *
 * @author giorgospetkakis
 *
 */
public abstract class WeightCacheManager {

    private static final String DIR = "weight-cache/";

    private static final String FILE_PREFIX = "TakAIWeights-";

    private static final String FILE_SUFFIX = ".csv";

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Logger logger = Logger.getLogger(WeightCacheManager.class);

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
     * @param approx The value approximator to be recorded
     */
    public static void record(ValueApproximator approx) {
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

        String gamecsv = getApproximatorWeights(approx);

        try {
            logger.debug("Attempting to record weights for: " + approx.hashCode() + " to file " + CURRENT_RECORDS_FILE);
            writeToFile(CURRENT_RECORDS_FILE, gamecsv);
            logger.info("Successfully recorded weights for: " + approx.hashCode() + " to file " + CURRENT_RECORDS_FILE);
        } catch (FileNotFoundException fnfe) {
            logger.error("Could not find file " + CURRENT_RECORDS_FILE);
            createNewRecordsFile();
            record(approx);
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
                writeToFile(CURRENT_RECORDS_FILE, "");
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
     * Number of turns that have passed
     * Time elapsed since the start of the game
     *
     * @param approx The value approximator to return weights for
     * @return String CSV format of game metadata
     */
    private static String getApproximatorWeights(ValueApproximator approx) {
        String ret = "\n";
<<<<<<< HEAD
        //ret += AlphaBetaAI.calculateHeuristic(GameManager.getCurrent(), true);
=======
        ret += AlphaBetaAI.calculateHeuristic(GameManager.getCurrent(), true);
>>>>>>> c4c69f76971b9d99714086a414a76e76e83ad4ed
        for (double w : approx.getWeights()) {
            ret +=  w + ",";
        }
        ret = ret.substring(0, ret.length()-1);
        return ret;
    }
//
//    public static String readMostRecentWeights() throws IOException{
//
//        File file = new File(CURRENT_RECORDS_FILE);
//
//
//        RandomAccessFile stream = new RandomAccessFile(file, "r");
//        String curr = " ";
//        int pos = 1;
//        while(curr != "\n") {
//            stream.seek();
//        }
//        FileChannel channel = stream.getChannel();
//    }
}
