package breakthrough.ui;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.Color;
import breakthrough.domain.Move;
import breakthrough.domain.Position;

import java.io.*;

/** A command line interpreter (CLI) user interface. This
 * old fashioned UI which simply accepts commands
 * from the user is sufficient for manual testing
 * of the various implementations of Broker
 * based distribution solutions for Breakthrough.
 */
public class ClientInterpreter {

  private PrintStream systemOut;
  private InputStream systemIn;

  private Breakthrough game;

  public ClientInterpreter(Breakthrough game,
                           InputStream inputStream,
                           PrintStream outputStream) {
    this.game = game;

    systemIn = inputStream;
    systemOut = outputStream;
  }

  /**
   * The classic 'read command, evaluate command, loop' of a shell. Issue your
   * command, and see the result in the shell.
   */
  public void readEvalLoop() {
    systemOut.println("\n== Welcome to Breakthrough ==");

    BufferedReader bf = new BufferedReader(new InputStreamReader(systemIn));

    systemOut
            .println("Entering command loop, type \"q\" to quit, \"h\" for help.");

    // and enter the command processing loop
    String line;
    try {
      do {
        line = bf.readLine();
        if (line.length() > 0) {
          // split into into tokens on whitespace
          String[] tokens = line.split("\\s");

          // First handle the 'short hand' notation for movement
          if (tokens.length == 1) {
            char primaryCommand = line.charAt(0);
            handleNoArgumentCommand(primaryCommand);

          } else {
            handleMultiArgumentCommand(tokens[0], tokens);
          }
          systemOut.println();
        }
      } while (!line.equals("q"));
    } catch (IOException e) {
      systemOut.println("Exception caught: " + e);
    }
    systemOut.println("Leaving Breakthrough - Goodbye.");
  }

  private void handleNoArgumentCommand(char primaryCommand) {
    switch (primaryCommand) {
      // Print
      case 'p': {
        printBoard();
        break;
      }
      // Help
      case 'h': {
        showHelp();
        break;
      }
      // Quit
      case 'q': {
        systemOut.println("Logging out not supported, just kill the server.");
        break;
      }
      default: {
        systemOut.println("I do not understand that command. (Type 'h' for help)");
      }
    }

  }

  private void printBoard() {
    systemOut.println("   0 1 2 3 4 5 6 7 ");
    systemOut.println("   ----------------");
    for (int row = 7; row >= 0; row--) {
      systemOut.print( row + ": ");
      for (int col = 0; col < 8; col++) {
        Color c = game.getPieceAt(new Position(row,col) );
        char colorChar = '-';
        if (c == Color.BLACK) colorChar = 'B';
        if (c == Color.WHITE) colorChar = 'W';
        systemOut.print(colorChar+" ");
      }
      systemOut.println();
    }
    systemOut.println("   ----------------");
    systemOut.println("   0 1 2 3 4 5 6 7 ");

    systemOut.println();
    systemOut.println("Next Player: "
            + game.getPlayerInTurn());
  }

  private void handleMultiArgumentCommand(String token, String[] tokens) {
    if (token.equals("m") && tokens.length == 5) {
      int fr, fc, tr, tc;
      try {
        fr = Integer.parseInt(tokens[1]);
        fc = Integer.parseInt(tokens[2]);
        tr = Integer.parseInt(tokens[3]);
        tc = Integer.parseInt(tokens[4]);
      } catch (NumberFormatException e) {
        systemOut.println("One or more parameters are not numbers.");
        return;
      }
      systemOut.print("Move pawn (" + fr + "," + fc
              + ") to (" + tr + "," + tc + ") is ");
      Move move = new Move( new Position(fr, fc), new Position(tr, tc));
      boolean isValid = game.move(move);
      systemOut.println( isValid ? "VALID" : "INVALID");
    } else {
      systemOut.println("The format of the move command was wrong. (Type 'h' for help)");
    }
  }

  private void showHelp () {
    systemOut.println("=== Help on the Breakthrough commands. ===");
    systemOut.println("  Many commonly used commands are single-character");
    systemOut.println("Commands:");
    systemOut.println(" p              :  PRINT, print board layout;");
    systemOut.println(" m fr fc tr tc  :  MOVE, pawn from (fr,fc) to (tr,tc);");
    systemOut.println(" h              :  HELP, print this help instructions;");
    systemOut.println();
    systemOut.println("BoardState Layout:");
    systemOut.println("  Horizontal rows from row 0 to row 7.");
    systemOut.println("  Vertical columns from column 0 to column 7.");
    systemOut.println("  White and Black pawns are 'W' and 'B' respectively.");
  }

}
