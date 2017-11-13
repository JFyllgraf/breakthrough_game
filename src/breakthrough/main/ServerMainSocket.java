package breakthrough.main;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.BreakthroughSurrogate;
import frs.broker.Invoker;
import frs.broker.ipc.socket.SocketServerRequestHandler;

/** App server, using socket based implementations of broker roles.
 * The server is hardwired to port 37321.
 */
public class ServerMainSocket {
  
  public static void main(String[] args) throws Exception {
    new ServerMainSocket(37321); // No error handling!
  }

  public ServerMainSocket(int port) throws Exception {
    // Create a single servant object
    Breakthrough game = new BreakthroughSurrogate();

    // TODO: Fill in the code to configure the srh.
    SocketServerRequestHandler srh = null;

    // Welcome
    System.out.println("=== Breakthrough Socket (port:"
            + port + ") ===");
    System.out.println(" Use ctrl-c to terminate!");

    srh.run();

  }
}
