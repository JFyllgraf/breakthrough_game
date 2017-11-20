package breakthrough.marshall;

import breakthrough.domain.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frs.broker.ReplyObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by csdev on 11/17/17.
 */
public class InvokerImpl implements frs.broker.Invoker {
    private final Breakthrough breakthrough;
    private final Gson gson;

    public InvokerImpl(Breakthrough breakthroughServant) {
        this.breakthrough = breakthroughServant;
        gson = new Gson();


    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();


        if (operationName.equals(BTNames.GET_WINNER)) {
            Color winner = breakthrough.getWinner();
            reply = new ReplyObject(HttpServletResponse.SC_OK,
                    gson.toJson(winner));

        } else if (operationName.equals(BTNames.GET_PLAYER_IN_TURN)){
            Color playerInTurn = breakthrough.getPlayerInTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK,
                    gson.toJson(playerInTurn));

        } else if (operationName.equals(BTNames.GET_PIECE_AT)){
            Position position = gson.fromJson(array.get(0), Position.class);
            Color piece = breakthrough.getPieceAt(position);

            reply = new ReplyObject(HttpServletResponse.SC_OK,
                    gson.toJson(piece));

        }else if (operationName.equals(BTNames.MOVE)){
            Move move = gson.fromJson(array.get(0), Move.class);
            Boolean canMove = breakthrough.move(move);

            reply = new ReplyObject(HttpServletResponse.SC_OK,
                    gson.toJson(canMove));
        }

        else {
            reply = new ReplyObject(HttpServletResponse.SC_NOT_IMPLEMENTED,
                    "Unknown operation name: " + operationName);
        }
        return reply;
    }


}
