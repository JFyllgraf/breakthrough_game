package breakthrough.domain;

/**
 * A 'surrogate' implementation for a Breakthrough game, the rules are not
 * really obeyed, but it has enough behavior to test most aspects of a Broker
 * implementation of the game - like invalid moves, getting contents of the
 * board, etc. Winner condition is not implemented for obvious reasons.
 *
 * It is kind of a test double, but does not match any of Meszaros' categories,
 * thus it is named a surrogate.
 */
public class BreakthroughSurrogate implements Breakthrough {
  GameState state;

  public BreakthroughSurrogate() {
    state = new GameState();
  }

  @Override
  public Color getPieceAt(Position p) {
    return state.get(p);
  }

  @Override
  public Color getPlayerInTurn() {
    return state.getPlayerInTurn();
  }

  @Override
  public Color getWinner() {
    // We do not test winner conditions in our surrogate
    return Color.NONE;
  }

  @Override
  public boolean move(Move m) {
    // The only invalid move is trying to move an opponent's piece (or
    // no piece at all).
    if (state.get(m.getFrom()) != getPlayerInTurn()) return false;

    // Update data structure; note that rules are not observed
    state.doMove(m.getFrom(), m.getTo());

    // Change turns
    state.togglePlayerInTurn();

    return true;
  }

  /** 
   * Get the inner state object for marshalling.
   * @return the state object of the game.
   */
  public GameState getBoardState() {
    return state;
  }
}
