package brokerTests;

import breakthrough.client.BreakthroughProxy;
import breakthrough.domain.*;
import breakthrough.marshall.InvokerImpl;
import doubles.LocalMethodCallClientRequestHandler;
import frs.broker.Invoker;
import frs.broker.ReplyObject;
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

    Move moveStub;

    @Before
    public void setup(){
        servant = new StubServant();
        invoker = new InvokerImpl(servant);
        handler = new LocalMethodCallClientRequestHandler(invoker);
        requestor = new StandardJSONRequestor(handler);
        clientProxy = new BreakthroughProxy(requestor);

        moveStub = new Move(new Position(2,2), new Position(2,3));
    }

    @Test
    public void shouldBeBlackForTestStubGetWinner(){
        assertThat(clientProxy.getWinner(), is(Color.BLACK));
    }

    @Test
    public void shouldReturnWhiteForTestStubGetPlayerInTurn(){
        assertThat(clientProxy.getPlayerInTurn(), is(Color.WHITE));
    }

    @Test
    public void shouldBeBlackForTestStubGetPieceAt(){
        assertThat(clientProxy.getPieceAt(new Position(1,1)), is(Color.BLACK));
    }

    @Test
    public void shouldReturnCorrectPositionParameterForTestStubGetPieceAt(){
        clientProxy.getPieceAt(new Position(1,1));
        assertThat(servant.position.toString(), is("Position{row=1, column=1}"));
    }

    @Test
    public void shouldReturnCorrectMoveParameterForTestStubMove(){
        clientProxy.move(moveStub);
        assertThat(servant.move.toString(), is(moveStub.toString()));
    }

    @Test
    public void shouldReturnTrueForTestStubMove(){
       assertThat(clientProxy.move(moveStub), is(true));
    }

    private class StubServant implements Breakthrough, Servant{

        private Position position;
        private Move move;

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
            this. move = move;
            return true;
        }
    }

}
