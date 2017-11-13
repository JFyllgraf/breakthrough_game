package breakthrough.domain;

/**
 * A record type/PODO that encapsulates the board state of
 * a breakthrough game. 
 */
public class GameState {
  // The actual data structure in a format
  // that is easily marshalled.
  private Color[][] board = new Color[8][8];
  private Color playerInTurn;

  public GameState() {
    // Setup game board
    setAllInRowTo(0, Color.BLACK);
    setAllInRowTo(1, Color.BLACK);
    for (int i = 2; i <= 5; i++)
      setAllInRowTo(i, Color.NONE);
    setAllInRowTo(6, Color.WHITE);
    setAllInRowTo(7, Color.WHITE);
    // Initially white is in turn
    playerInTurn = Color.WHITE;
  }

  private void setAllInRowTo(int row, Color value) {
    for ( int column = 0; column < 8; column++ ) {
      board[row][column] = value;
    }
  }

  public Color get(Position position) {
    return board[position.getRow()][position.getColumn()];
  }

  public Color getPlayerInTurn() {
    return playerInTurn;
  }

  /** The raw 'move' of a piece is actually just copy
   * contents of from to the to position while putting
   * NONE in the from position
   * @param from the 'from' position
   * @param to the 'to' position
   */
  public void doMove(Position from, Position to) {
    board[to.getRow()][to.getColumn()] = board[from.getRow()][from.getColumn()];
    board[from.getRow()][from.getColumn()] = Color.NONE;
  }

  /**
   * Toogle between white and black player in turn.
   */
  public void togglePlayerInTurn() {
    playerInTurn = (playerInTurn == Color.WHITE ? Color.BLACK : Color.WHITE);
  }
}
