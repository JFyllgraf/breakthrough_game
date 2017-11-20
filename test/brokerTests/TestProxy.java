package brokerTests;

import breakthrough.client.BreakthroughProxy;
import breakthrough.domain.*;
import frs.broker.ClientProxy;
import frs.broker.Requestor;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
/**
 * Created by csdev on 11/14/17.
 */
public class TestProxy {
    TestSpyRequestor spy;
    Breakthrough bt;

    @Before
    public void setup(){
        // Create a test spy for the requestor,
        // and inject it into the client proxy
        spy = new TestSpyRequestor();
        bt = new BreakthroughProxy(spy);
    }

    @Test
    public void shouldValidateClientProxyCallingRequestorForGetWinner() {
        // Invoke a method
        Color c =  bt.getWinner();

        // and validate that the client proxy produce
        // proper indirect output
        assertThat(spy.lastOID, is("42"));
        assertThat(spy.lastOperationName, is(BTNames.GET_WINNER));
    }

    @Test
    public void shouldValidateClientProxyCallingRequestorForGetPlayerInTurn() {
        Color c =  bt.getPlayerInTurn();

        assertThat(spy.lastOID, is("42"));
        assertThat(spy.lastOperationName, is(BTNames.GET_PLAYER_IN_TURN));
    }

    @Test
    public void shouldValidateClientProxyCallingRequestorForGetPieceAt() {
        Color c =  bt.getPieceAt(new Position(1,1));

        assertThat(spy.lastOID, is("42"));
        assertThat(spy.lastOperationName, is(BTNames.GET_PIECE_AT));
    }

    @Test
    public void shouldValidateClientProxyCallingRequestorForMove(){
        Move move = new Move(new Position(6,0), new Position(5,0));
        Boolean canMove = bt.move(move);

        assertThat(spy.lastOID, is("42"));
        assertThat(spy.lastOperationName, is(BTNames.MOVE));
    }

    private class TestSpyRequestor implements Requestor {
        private String lastOID;
        private String lastOperationName;

        @Override
        public <T> T sendRequestAndAwaitReply(String objectId, String operationName, Type typeOfReturnValue, Object... argument) {
            lastOID = objectId;
            lastOperationName = operationName;
            return null;
        }
    }
}
