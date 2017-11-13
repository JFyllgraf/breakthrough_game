package breakthrough.domain;

/**
 * Record type for a position on the Breakthrough board.
 */

public class Position {

  private int row;
  private int column;

  /**
   * Construct position. PRECONDITION: Both
   * row and column must be in interval 0..7.
   * @param row the row
   * @param column the column
   */
  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Get the row
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Get the column
   * 
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  /**
   * Return the string representation of this position.
   * 
   * @return a string representation
   */
  @Override
  public String toString() {
    return "Position{" +
            "row=" + row +
            ", column=" + column +
            '}';
  }
}
