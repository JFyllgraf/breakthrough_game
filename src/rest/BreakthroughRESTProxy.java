package rest;

import breakthrough.domain.*;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import frs.broker.ClientProxy;
import frs.broker.IPCException;
import frs.broker.ipc.http.MimeMediaType;

import javax.servlet.http.HttpServletResponse;

public class BreakthroughRESTProxy implements Breakthrough, ClientProxy {

    private final String baseURL;
    private String id;
    private Gson gson = new Gson();
    private GameState gameState;

    public BreakthroughRESTProxy(String hostname, int port){
        baseURL = "http://" + hostname + ":" + port + "/";

        HttpResponse<JsonNode> jsonResponse = null;

        String path = Constants.BREAKTHROUGH_PATH;

        try {
            jsonResponse = Unirest.post(baseURL + path).
                    header("Accept", MimeMediaType.APPLICATION_JSON).
                    header("Content-Type", MimeMediaType.APPLICATION_JSON).asJson();

        } catch (UnirestException exception){
            throw new IPCException("POST failed", exception);
        }

        int statusCode = jsonResponse.getStatus();
        if (statusCode != HttpServletResponse.SC_CREATED){
            throw new IPCException("POST did not return CREATED but "+ statusCode);
        }

        String location = jsonResponse.getHeaders().getFirst("Location");

        String parts[] = location.split("/");
        String id = parts[parts.length-1];

        this.id = id;
    }

    public BreakthroughRESTProxy(String hostname, int port, String gameId){
        this.baseURL = "http://" + hostname + ":" + port + "/";

        id = gameId;
    }

    public GameState getGameState(){
        HttpResponse<JsonNode> jsonResponse = null;

        String path = Constants.BREAKTHROUGH_PATH + id;

        try {
            jsonResponse = Unirest.get(baseURL + path).
                    header("Accept", MimeMediaType.APPLICATION_JSON).
                    header("Content-Type", MimeMediaType.APPLICATION_JSON).asJson();
        } catch (UnirestException exception){
            throw new IPCException("GET failed", exception);
        }

        int statusCode = jsonResponse.getStatus();
        gameState = null;
        if(statusCode == HttpServletResponse.SC_OK){
            String payload = jsonResponse.getBody().toString();
            gameState = gson.fromJson(payload, GameState.class);
        }
        return gameState;
    }

    @Override
    public Color getPieceAt(Position p) {
        return getGameState().get(p);
    }

    @Override
    public Color getPlayerInTurn() {
        return getGameState().getPlayerInTurn();
    }

    @Override
    public Color getWinner() {
        HttpResponse<String> jsonResponse = null;

        String path = Constants.BREAKTHROUGH_PATH + id + "/winner";

        try {
            jsonResponse = Unirest.get(baseURL + path).
                    header("Accept", MimeMediaType.APPLICATION_JSON).
                    header("Content-Type", MimeMediaType.APPLICATION_JSON).asString();
        } catch (UnirestException exception){
            throw new IPCException("GET failed", exception);
        }

        int statusCode = jsonResponse.getStatus();
        Color color = null;
        if(statusCode == HttpServletResponse.SC_OK){
            String payload = jsonResponse.getBody();
            color = gson.fromJson(payload, Color.class);
        }
        return color;
    }

    @Override
    public boolean move(Move move) {
        HttpResponse<JsonNode> jsonResponse= null;
        String payload = gson.toJson(move);
        String path = Constants.BREAKTHROUGH_PATH + id;

        try {
            jsonResponse = Unirest.put(baseURL + path).
                    header("Accept", MimeMediaType.APPLICATION_JSON).
                    header("Content-Type", MimeMediaType.APPLICATION_JSON).
                    body(payload).asJson();
        } catch (UnirestException exception){
            throw new IPCException("PUT failed", exception);
        }

        int statusCode = jsonResponse.getStatus();

        return statusCode == HttpServletResponse.SC_OK;
    }
}
