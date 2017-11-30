package breakthrough.main;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.BreakthroughSurrogate;
import rest.BreakthroughRESTserver;
import rest.RESTAdapter;

/** App server for Breakthrough, using REST.
 */
public class ServerMainREST {
  
  public static void main(String[] args) throws Exception {
    new ServerMainREST(); // No error handling!
  }

  public ServerMainREST() throws Exception {
    int port = 4567;

    // TODO: Create the REST based server instance

    RESTAdapter adapter = new RESTAdapter();
    BreakthroughRESTserver server = new BreakthroughRESTserver(port, adapter);

    // Welcome
    System.out.println("=== Breakthrough REST (port:"+port+") ===");
    System.out.println(" Use ctrl-c to terminate!"); 

    // and start it by registrering the routes to listen to
    // ala 'serverRequestHandler.registerRoutes();'
    server.registerRoutes();

  }
}
