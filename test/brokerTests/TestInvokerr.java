package brokerTests;

import breakthrough.client.BreakthroughProxy;
import breakthrough.domain.Breakthrough;
import breakthrough.domain.Color;
import breakthrough.domain.Move;
import breakthrough.domain.Position;
import breakthrough.marshall.InvokerImpl;
import doubles.LocalMethodCallClientRequestHandler;
import frs.broker.Invoker;
import frs.broker.Servant;
import frs.broker.marshall.json.StandardJSONRequestor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestInvokerr {

    Breakthrough clientProxy;
    StandardJSONRequestor requestor;
    LocalMethodCallClientRequestHandler handler;
    Invoker invoker;
    StubServant servant;

    @Before
    public void setup(){
        servant = new StubServant();
        invoker = new InvokerImpl(servant);
        handler = new LocalMethodCallClientRequestHandler(invoker);
        requestor = new StandardJSONRequestor(handler);
        clientProxy = new BreakthroughProxy(requestor);
    }

    @Test
    public void shouldBeBlackForTestStubGetWinner(){
        assertThat(clientProxy.getWinner(), is(Color.BLACK));
    }

    @Test
    public void shouldBeBlackForTestStubGetPieceAt(){
        assertThat(clientProxy.getPieceAt(new Position(1,1)), is(Color.BLACK));
    }

    @Test
    public void shouldBePosition1_1ForTestStubGetPieceAt(){
        clientProxy.getPieceAt(new Position(1,1));
        assertThat(servant.position.toString(), is("Position{row=1, column=1}"));
    }

    private class StubServant implements Breakthrough, Servant{

        private Position position;

        @Override
        public Color getPieceAt(Position p) {
            position = p;
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
