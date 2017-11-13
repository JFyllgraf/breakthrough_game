package breakthrough.main;

import breakthrough.domain.Breakthrough;
import breakthrough.ui.ClientInterpreter;
import frs.broker.ClientRequestHandler;
import frs.broker.Requestor;
import frs.broker.ipc.socket.SocketClientRequestHandler;
import frs.broker.marshall.json.StandardJSONRequestor;

/** Client for breakthrough, using socket communication.
 * Hardcoded for port 37321, host given as argument.
 */
public class ClientMainSocket {
  public static void main(String args[]) {
    new ClientMainSocket(args[0], 37321);
  }

  public ClientMainSocket(String host, int port) {
    // Create the game
    Breakthrough game = null;

    // TODO: Fill in the code to solve the exercise

    // Welcome
    System.out.println("=== Client Socket. Host = "
            + host + " ===");
    // And start the interpreter...
    ClientInterpreter interpreter =
            new ClientInterpreter(game,
                    System.in, System.out);
    interpreter.readEvalLoop();
  }
}
