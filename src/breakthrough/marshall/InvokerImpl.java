package breakthrough.marshall;

import breakthrough.domain.BTNames;
import breakthrough.domain.Breakthrough;
import breakthrough.domain.Color;
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

        try {
            if (operationName.equals(BTNames.GET_WINNER)) {
                Color winner = breakthrough.getWinner();
                reply = new ReplyObject(HttpServletResponse.SC_OK,
                        gson.toJson(winner));
            } else {
                reply = new ReplyObject(HttpServletResponse.SC_NOT_IMPLEMENTED,
                        "Unknown operation name: " + operationName);
            }

        } catch (Exception e) {
            reply = new ReplyObject(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
        return reply;
    }


}
