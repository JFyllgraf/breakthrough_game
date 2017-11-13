package breakthrough.domain;

/**
 * Facade for a Breakthrough game. The chess board is organized in 8 rows of 8
 * columns matrix, numbered 0..7. Black has pawns on row 0 and 1 while white has
 * pawns on row 6 and 7. One way to visualize it is as shown here:
 * <pre>
   0 1 2 3 4 5 6 7
   ----------------
7: W W W W W W W W
6: W W W W W W W W
5: - - - - - - - -
4: - - - - - - - -
3: - - - - - - - -
2: - - - - - - - -
1: B B B B B B B B
0: B B B B B B B B
   ----------------
   0 1 2 3 4 5 6 7
</pre>
 * 
 */
public interface Breakthrough {

  /**
   * Return the type of piece on a given position on the chess board.
   *
   * @return the type of piece on the location.
   */
  Color getPieceAt(Position p);

  /**
   * Return the player that is in turn, i.e. allowed to move.
   *
   * @return the player that may move a piece next
   */
  Color getPlayerInTurn();

  /**
   * Return the winner of the game.
   *
   * @return the winner of the game or null in case no winner has been found
   *         yet.
   */
  Color getWinner();

  /**
   * Validate and potentially make a move. A move is invalid if you try to move
   * your opponent's pieces or the move does not follow the rules, see the
   * exercise specification. PRECONDITION: the (row,column) coordinates are
   * valid positions, that is, all between (0..7).
   *
   * @return true if the move is valid, false otherwise
   */
  boolean move(Move move);

}
