package breakthrough.domain;

/**
 * The states possible for a (proposed) move. Any move starts as PROPOSED when
 * clients request it; and the game then either marks the move as ACCEPTED or
 * REJECTED based upon validity of the move given the current game state.
 */
public enum MoveState {
  PROPOSED, // A move has been proposed, but not validated
  ACCEPTED, // A move has been validated and accepted as valid
  REJECTED  // A move has been validated and rejected as invalid
}
