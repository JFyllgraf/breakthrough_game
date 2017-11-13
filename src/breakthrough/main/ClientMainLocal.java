package breakthrough.main;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.BreakthroughSurrogate;
import breakthrough.ui.ClientInterpreter;

/**
 * Client for breakthrough that is local, it just uses the Servant directly.
 */
public class ClientMainLocal {
  public static void main(String args[]) {
    // Create the game
    Breakthrough game;
    game = new BreakthroughSurrogate();

    // Welcome
    System.out.println("=== Client Local ===");
    // And start the interpreter...
    ClientInterpreter interpreter =
            new ClientInterpreter(game,
                    System.in, System.out);
    interpreter.readEvalLoop();
  }
}
