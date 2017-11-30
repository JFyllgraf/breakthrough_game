package rest;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.Color;
import breakthrough.domain.GameState;
import breakthrough.domain.Move;
import com.google.gson.Gson;
import frs.broker.ServerRequestHandler;
import frs.broker.ipc.http.MimeMediaType;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import static spark.Spark.*;

public class BreakthroughRESTserver implements ServerRequestHandler{

    private final int port;
    private int lastStatusCode;
    private final Gson gson;
    private final RESTAdapter adapter;

    public BreakthroughRESTserver(int portNumber, RESTAdapter servant){
        this.adapter = servant;
        this.port = portNumber;

        gson = new Gson();
    }

    public void registerRoutes(){
        port(port);

        // POST = create game
        String storeRoute = "/" + Constants.BREAKTHROUGH_PATH;
        post(storeRoute, (req,res) -> {
            String id = adapter.postOnBreakthrough();

            int statusCode = HttpServletResponse.SC_CREATED;
            res.status(statusCode);
            res.type(MimeMediaType.APPLICATION_JSON);
            res.header("Location", req.host() + "/" + Constants.BREAKTHROUGH_PATH + id);

            lastStatusCode = statusCode;

            return "{}";
        });


        String getRoute = "/" + Constants.BREAKTHROUGH_PATH + ":id";

        get(getRoute, (req, res) -> {
            String gameId = req.params(":id");

            GameState gameState = adapter.getOnBreakthrough(gameId);

            String returnValue = null;

            if (gameState == null){
                lastStatusCode = HttpServletResponse.SC_NOT_FOUND;
                returnValue = "{}";
            } else {
                lastStatusCode = HttpServletResponse.SC_OK;
                returnValue = gson.toJson(gameState, GameState.class);

            }

            res.status(lastStatusCode);
            res.type(MimeMediaType.APPLICATION_JSON);

            return returnValue;
        });

        String correctRoute = "/" + Constants.BREAKTHROUGH_PATH + ":id";

        put(correctRoute, (req, res) -> {
            String body = req.body();
            String gameId = req.params(":id");

            Move move = gson.fromJson(body, Move.class);

            boolean isValid = adapter.putOnBreakthrough(move, gameId);

            lastStatusCode = HttpServletResponse.SC_OK;

            if(!isValid){
                lastStatusCode = HttpServletResponse.SC_CONFLICT;
            }

            res.status(lastStatusCode);
            res.type(MimeMediaType.APPLICATION_JSON);

            return "{}";
        });

        String getWinnerRoute = "/" + Constants.BREAKTHROUGH_PATH + ":id" + "/winner";
        get(getWinnerRoute, (req, res) -> {
            String gameId = req.params(":id");

            Color color = adapter.getOnBreakthoughWinner(gameId);

            String returnValue = null;

            if (color == null){
                lastStatusCode = HttpServletResponse.SC_NOT_FOUND;
                returnValue = "{}";
            } else {
                lastStatusCode = HttpServletResponse.SC_OK;
                returnValue = gson.toJson(color, Color.class);
            }

            res.status(lastStatusCode);
            res.type(MimeMediaType.APPLICATION_JSON);

            return returnValue;
        });


    }
}
