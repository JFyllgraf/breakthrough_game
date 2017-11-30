package rest;

import breakthrough.domain.*;
import com.google.gson.Gson;

import java.util.HashMap;

public class RESTAdapter {

    HashMap<String, BreakthroughSurrogate> games;
    int idCounter;
    Gson gson;

    public RESTAdapter(){
        games = new HashMap<>();
        this.idCounter = 1;

        gson = new Gson();
    }

    public String postOnBreakthrough(){
        String id = "" + idCounter;
        games.put(id, new BreakthroughSurrogate());

        idCounter++;

        return id;
    }

    public GameState getOnBreakthrough(String id){
        if (games.containsKey(id)){
            BreakthroughSurrogate game = games.get(id);
            return game.getBoardState();
        }
        return null;
    }

    public boolean putOnBreakthrough(Move move, String id){
        if (games.containsKey(id)){
            BreakthroughSurrogate game = games.get(id);
            Boolean canMove = game.move(move);
            if (canMove){
                return true;
            }
        }
        return false;
    }

    public Color getOnBreakthoughWinner(String id){
        if (games.containsKey(id)){
            BreakthroughSurrogate game = games.get(id);
            return game.getWinner();
        }
        return null;
    }

}
