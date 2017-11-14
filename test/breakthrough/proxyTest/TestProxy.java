package breakthrough.proxyTest;

import breakthrough.client.BreakthroughProxy;
import breakthrough.domain.BTNames;
import breakthrough.domain.Breakthrough;
import breakthrough.domain.Color;
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

    @Test
    public void shouldValidateClientProxyCallingRequestorForGetWinner() {
        // Create a test spy for the requestor,
        // and inject it into the client proxy
        TestSpyRequestor spy = new TestSpyRequestor();
        Breakthrough bt = new BreakthroughProxy(spy);

        // Invoke a method
        Color c =  bt.getWinner();

        // and validate that the client proxy produce
        // proper indirect output

        assertThat(spy.lastOID, is("42"));
        assertThat(spy.lastOperationName, is(BTNames.GET_WINNER));
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
