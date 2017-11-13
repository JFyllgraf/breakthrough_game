package breakthrough.domain;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/** An incomplete test suite for the surrogate implementation
 * of Breakthrough.
<#if type == "code">
 
<#include "/data/author.txt">
</#if>
*/
public class TestBreakthroughSurrogate {
  private Breakthrough game;
  /** Fixture */
  @Before
  public void setUp() {
    game = new BreakthroughSurrogate();
  }

  @Test
  public void shouldHaveInitialBlackPawns(){
    // "Boundary tests" of black's initial positions
    assertThat(game.getPieceAt(new Position(0, 0)),
            is(Color.BLACK));
    assertThat(game.getPieceAt(new Position(0, 7)),
            is(Color.BLACK));
    assertThat(game.getPieceAt(new Position(1, 0)),
            is(Color.BLACK));
    assertThat(game.getPieceAt(new Position(1, 7)),
            is(Color.BLACK));

    assertThat(game.getPieceAt(new Position(2, 3)),
            is(not(Color.BLACK)));
    assertThat(game.getPieceAt(new Position(7, 0)),
            is(not(Color.BLACK)));
  }

  @Test
  public void shouldHaveInitialWhitePawns(){
    // "Boundary tests" of white's initial positions
    assertThat(game.getPieceAt(new Position(6, 0)),
            is(Color.WHITE));
    assertThat(game.getPieceAt(new Position(6, 7)),
            is(Color.WHITE));
    assertThat(game.getPieceAt(new Position(7, 0)),
            is(Color.WHITE));
    assertThat(game.getPieceAt(new Position(7, 7)),
            is(Color.WHITE));

    assertThat(game.getPieceAt(new Position(2, 3)),
            is(not(Color.WHITE)));
    assertThat(game.getPieceAt(new Position(1, 0)),
            is(not(Color.WHITE)));
  }

  @Test
  public void shouldHandleValidAndInvalidMoves() {
    // Spot testing of valid and invalid moves
    assertThat(game.getPlayerInTurn(), is(Color.WHITE));

    Position f = new Position(6,5),
    t = new Position(5,5);
    assertThat(game.move( new Move(f, t)),
            is(true));
    // Validate that move was made
    assertThat(game.getPieceAt(f), is(Color.NONE));
    assertThat(game.getPieceAt(t), is(Color.WHITE));

    // second move is illegal as it is black's turn
    assertThat(game.getPlayerInTurn(), is(Color.BLACK));
    f = new Position(5,5); t = new Position(4,5);
    assertThat(game.move( new Move(f,t)),
            is(false));

    // do not allow moving non existing pawns
    assertThat(game.move(new Move(new Position(2,2), new Position(3,3))),
            is(false));

    // black can make move
    assertThat(game.move(new Move(new Position(1,1), new Position(2,2))),
            is(true));
    // Validate that move was made
    assertThat(game.getPieceAt(new Position(1,1)), is(Color.NONE));
    assertThat(game.getPieceAt(new Position(2,2)), is(Color.BLACK));
  }
}
