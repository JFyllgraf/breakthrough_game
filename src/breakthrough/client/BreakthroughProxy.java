package breakthrough.client;

import breakthrough.domain.*;
import frs.broker.ClientProxy;
import frs.broker.Requestor;

/**
 * Created by csdev on 11/14/17.
 */
public class BreakthroughProxy implements Breakthrough, ClientProxy {

    private final Requestor requestor;

    public BreakthroughProxy(Requestor req){
        this.requestor = req;
    }

    @Override
    public Color getPieceAt(Position p) {
        return null;
    }

    @Override
    public Color getPlayerInTurn() {
        return null;
    }

    @Override
    public Color getWinner() {
        Color winner = requestor.sendRequestAndAwaitReply(BTNames.OID, BTNames.GET_WINNER, Color.class);
        return winner;
    }

    @Override
    public boolean move(Move move) {
        return false;
    }
}
