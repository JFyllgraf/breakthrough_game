package breakthrough.main;

import breakthrough.domain.Breakthrough;
import breakthrough.ui.ClientInterpreter;

/** Client for breakthrough, using REST.
 */
public class ClientMainREST {
  public static void main(String args[]) {
    System.out.println("=== Client REST ===");

    String host = args[0];
    String op = args[1];
    String location = args[2];
    System.out.println(" Op: " + op + ", Location: "+ location);

    // TODO: Replace the concrete type of the game with your
    // REST proxy type
    Breakthrough game = null;

    if (op.equals("create")) {
      // TODO: Create a Breakthrough REST proxy that CREATES a game resource
    } else {
      // TODO: Create a Breakthrough REST proxy that connects to a
      // game resource on a specific Location
    }
    
    // Start the interpreter
    ClientInterpreter interpreter =
        new ClientInterpreter(game,
                System.in, System.out);
    interpreter.readEvalLoop();

  }
}
