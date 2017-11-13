package frs.broker.ipc.http;

import static spark.Spark.*;

import com.google.gson.Gson;

import frs.broker.*;

/** ServerRequestHandler implementation using HTTP and URI Tunneling.
 * NOTE! Spark-Java likes static methods too much which are
 * contradictionary to flexible delegation based designs as
 * their bindings are made too early. You therefore have to
 * A) Instantiate the SRH and next B) invoke the srh.registerRoutes()
 * method.
 * <p>
 * Implementation based on the Spark-Java framework.
 */
public class UriTunnelServerRequestHandler
        implements ServerRequestHandler {

  protected Gson gson;
  protected Invoker invoker;
  protected int port;
  protected int lastStatusCode;
  protected String lastVerb;
  private String tunnelRoute;

  public UriTunnelServerRequestHandler(Invoker invoker,
                                       int port, String tunnelRoute) {
    this.invoker = invoker;
    this.port = port;
    this.tunnelRoute = tunnelRoute;
    gson = new Gson();
  }

  public void registerRoutes() {
    // Set the port to listen to
    port(port);

    // POST is for all incoming requests
    post(tunnelRoute, (req,res) -> {
      String body = req.body();
      
      // The incoming body is a full request
      // object to be demarshalled
      RequestObject p = gson.fromJson(body, RequestObject.class);

      ReplyObject reply = invoker.handleRequest(p.getObjectId(),
              p.getOperationName(), p.getPayload());

      // Store the last verb and status code to allow spying during test
      lastVerb = req.requestMethod();
      lastStatusCode = reply.getStatusCode();
      
      res.status(reply.getStatusCode());
      res.type(MimeMediaType.APPLICATION_JSON);

      return gson.toJson(reply);
    });

  }

  public void closedown() {
    stop();
  }
  
  /**
   * Return status code of last operation. A test retrieval interface.
   * 
   * @return last status code
   */
  public int lastStatusCode() {
    return lastStatusCode;
  }

  public String lastHTTPVerb() {
    return lastVerb;
  }

}
