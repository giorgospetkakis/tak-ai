package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import beans.Capstone;
import beans.Cell;
import beans.Piece;
import beans.Player;
import beans.Stone;
import game.BoardManager;
import game.Game;
import players.DummyPlayer;
import run.GameManager;

/**
 * Reads in a game file from a file and instantiates it.
 * 
 * @author giorgospetkakis
 *
 */
public abstract class GameFileManager {

  private static final String DIR = "resources/";

  private static final String SUB_DIR = "/sample-boards/";

  private static final String FILE_SUFFIX = ".tak";

  private static final Logger logger = Logger.getLogger(GameFileManager.class);

  /**
   * Creates a game from a .tak file.
   * 
   * @param path The relative path of the file being read
   * @return The game being created
   * @throws IOException
   */
  public static Game loadGame(String path) throws IOException {

    // Check if we're trying to read the right file format
    if (!path.substring(path.length() - 3).equalsIgnoreCase("tak")) {
      throw new IOException("Cannot this read file format");
    }

    Game game = null;
    StringTokenizer tr;
    String line = "";

    // Attempt to read the file
    try {
      @SuppressWarnings("resource")
      BufferedReader br = new BufferedReader(new FileReader(new File(path)));

      // Parse first five lines of metadata: Game Type, Board Size, Player types and whose turn it
      // is
      GameManager.newGame(br.readLine(), Integer.parseInt(br.readLine()),
          Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
      game = GameManager.games.getLast();
      
      game.setNumTurns(Integer.parseInt(br.readLine()));
      
      game.setCurrent(game.getPlayer(Integer.parseInt(br.readLine())));

      // Parse board state
      line = br.readLine();
      while (line != null) {
        // Header is board position, body contains piece stack
        tr = new StringTokenizer(line, ":");
        String header = tr.nextToken();
        String body = tr.nextToken();

        // Find taget cell from header
        Cell target = BoardManager.getCell(game.getBoard(),
            Integer.parseInt(header.substring(0, 1)), Integer.parseInt(header.substring(1)));
        tr = new StringTokenizer(body, ",");

        // Add pieces to the cell
        while (tr.hasMoreTokens()) {
          String input = tr.nextToken();
          Piece piece = new Stone(new DummyPlayer(), false);
          Player player = game.getPlayer(Integer.parseInt(input.substring(0, 1)));

          // Find piece type from special characters
          if (input.length() > 1) {
            if (input.charAt(1) == '/') {
              piece = player.getStone(true);
            } else if (input.charAt(1) == '!') {
              piece = player.getCapstone();
            }
          } else {
            piece = player.getStone(false);
          }
          BoardManager.add(game.getBoard(), piece, target);
        }
        line = br.readLine();
      }
    } catch (FileNotFoundException e) {
      logger.error("Could not find file " + path);
    } catch (IOException ioe) {
      logger.error("Could not parse file " + path);
    } catch (NumberFormatException nfe) {
      logger.error("File damaged or corrupted " + path);
    }
    return game;
  }

  public static void saveGame(Game game) {
    String info = generateFileContent(game);
    
    String filename = createGameFile(game);
    if (filename == null) {
      return;
    }
    
    RandomAccessFile stream;
    try {
      stream = new RandomAccessFile(filename, "rw");
      FileChannel channel = stream.getChannel();
      stream.seek(stream.length());
      
      @SuppressWarnings("unused")
      FileLock lock = channel.tryLock();

      stream.writeChars(info);
      stream.close();
      channel.close();
    } catch (FileNotFoundException e) {
      logger.error("Could not find file " + filename);
    } catch (IOException e) {
      logger.error("Could not write to file " + filename);
    }
  }

  public static String generateFileContent(Game game) {
    String info = "";
    // Add board state
    Iterator<Cell> c = game.getBoard().getCells().values().iterator();
    
    while (c.hasNext()) {
      Cell cell = c.next();
      if(cell.isEmpty()) {
        continue;
      }
      
      info += (cell.getXpos()) + "" + (cell.getYpos()) + ":";

      Iterator<Piece> p = cell.getPieces().iterator();
      while (p.hasNext()) {
        game.incrementTurn();
        
        Piece piece = p.next();
        info += game.getPlayers().indexOf(piece.getOwner());

        if (piece instanceof Capstone) {
          info += "!";
        } else if (piece.isStandingStone()) {
          info += "/";
        }
        if (p.hasNext()) {
          info += ",";
        } else {
          info += "\n";
        }
      }
    }
    
    // This records the player who is going to play next (whose turn it is)
    game.setCurrent(game.getPlayer((game.getNumTurns()) % 2));
    
    // Add initial metadata
    info = game.getType() + "\n" + game.getBoard().getSize() + "\n"
        + game.getPlayer(0).getPlayerType() + "\n" + game.getPlayer(1).getPlayerType()
        + "\n" + game.getPlayers().indexOf(game.whoseTurn()) + "\n"
        + info;
    
    return info;
  }

  public static String createGameFile(Game game) {
    int fileIdx = 0;

    // Make descriptive filename
    String filename = DIR + game.getType().toLowerCase() + SUB_DIR + game.getBoard().getSize() + "-"
        + game.getPlayer(0).getPlayerType() + "-" + game.getPlayer(1).getPlayerType()
        + "-" + game.getNumTurns() + "-" + fileIdx + FILE_SUFFIX;

    while (new File(filename).exists()) {
      filename = filename.substring(0, filename.length() - 5) + ++fileIdx + FILE_SUFFIX;
    }

    // Create the file
    try {
      File file = new File(filename);
      if (file.createNewFile()) {
        logger.info("Created new sample board file: " + filename);
        return filename;
      }
    } catch (IOException ioe) {
      logger.error("Could not create file " + filename);
    }
    return null;
  }
}
