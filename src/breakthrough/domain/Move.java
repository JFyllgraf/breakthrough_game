package breakthrough.domain;

/**
 * Record type for a move in a Breakthrough game. Moves are proposed and then
 * either accepted or rejected.
 */
public class Move {
  private final Position from;
  private final Position to;
  private MoveState status;

  /**
   * Construct a proposed move.
   * @param from from position
   * @param to to position
   */
  public Move(Position from, Position to) {
    this.from = from;
    this.to = to;
    this.status = MoveState.PROPOSED;
  }

  /**
   * Return the from position.
   * @return from
   */
  public Position getFrom() {
    return from;
  }

  /**
   * Return the to position
   * @return to
   */
  public Position getTo() {
    return to;
  }

  /**
   * Return the status of this move.
   * @return the state of the move.
   */
  public MoveState getStatus() {
    return status;
  }

  /** Change the state of this move.
   * 
   * @param newState new state to enter.
   */
  public void changeState(MoveState newState) {
    status = newState;
  }
  
  /**
   * Return a clone of this move.
   * @return the cloned move
   */
  @Override
  public Move clone() {
    Move clone = new Move(getFrom(), getTo());
    clone.changeState(getStatus());
    return clone;
  }

  @Override
  public String toString() {
    return "Move{" +
            "from=" + from +
            ", to=" + to +
            ", status=" + status +
            '}';
  }

}
