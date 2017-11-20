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
        Color piece = requestor.sendRequestAndAwaitReply(BTNames.OID, BTNames.GET_PIECE_AT, Color.class, p);
        return piece;
    }

    @Override
    public Color getPlayerInTurn() {
        Color playerInTurn = requestor.sendRequestAndAwaitReply(BTNames.OID, BTNames.GET_PLAYER_IN_TURN, Color.class);
        return playerInTurn;
    }

    @Override
    public Color getWinner() {
        Color winner = requestor.sendRequestAndAwaitReply(BTNames.OID, BTNames.GET_WINNER, Color.class);
        return winner;
    }

    @Override
    public boolean move(Move move) {
        Boolean canMove = requestor.sendRequestAndAwaitReply(BTNames.OID, BTNames.MOVE, Boolean.class, move);
        if (canMove == null){
            return false;
        }
        return canMove;
    }
}
