package brokerTests;

import breakthrough.domain.*;
import breakthrough.marshall.InvokerImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frs.broker.Invoker;
import frs.broker.ReplyObject;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestInvoker {
    Invoker invoker;
    Breakthrough breakthrough;

    @Before
    public void setup(){
        breakthrough = new BreakthroughSurrogate();
        invoker = new InvokerImpl(breakthrough);
    }

    @Test
    public void shouldReturnBlackForBreakthroughTestStubGetWinner(){
        ReplyObject reply = invoker.handleRequest(BTNames.OID, BTNames.GET_WINNER, "[]");
        assertThat(reply.getPayload(), is("\"BLACK\""));
    }

    @Test
    public void shouldReturnWhiteForBreakthroughTestStubGetPlayerInTurn(){
        ReplyObject reply = invoker.handleRequest(BTNames.OID, BTNames.GET_PLAYER_IN_TURN, "[]");
        assertThat(reply.getPayload(), is("\"WHITE\""));
    }

    @Test
    public void shouldReturnBlackForBreakthroughTestStubGetPieceAt(){
        ReplyObject reply = invoker.handleRequest(BTNames.OID, BTNames.GET_PIECE_AT,  "[]");
        assertThat(reply.getPayload(), is(""));
        assertThat(reply.getStatusCode(), is("SC_OK"));
    }

    public class StubBreakthrough implements Breakthrough{

        @Override
        public Color getPieceAt(Position p) {
            return Color.BLACK;
        }

        @Override
        public Color getPlayerInTurn() {
            return Color.WHITE;
        }

        @Override
        public Color getWinner() {
            return Color.BLACK;
        }

        @Override
        public boolean move(Move move) {
            return false;
        }
    }

}
