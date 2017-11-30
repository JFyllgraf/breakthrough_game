package RESTAdapterTest;

import breakthrough.domain.*;
import org.junit.Before;
import org.junit.Test;
import rest.RESTAdapter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestRESTAdapter {

    RESTAdapter adapter;

    @Before
    public void setup(){
        adapter = new RESTAdapter();
    }

    @Test
    public void shouldStartWith1IDAndIncrement1ForEachPost(){
        assertThat(adapter.postOnBreakthrough(), is("1")); // Has created 1 game
        assertThat(adapter.postOnBreakthrough(), is("2")); // Has created 2 games
    }

    @Test
    public void shouldReturnABoardStateWhenAPostRequestHasBeenMade(){
        assertThat(adapter.getOnBreakthrough("1"), is(nullValue())); //no game has yet been made
        adapter.postOnBreakthrough();
        assertThat(adapter.getOnBreakthrough("1"), is(instanceOf(GameState.class)));
    }

    @Test
    public void shouldOnlyAllowValidMoves(){
        Move invalidMove = new Move(new Position(5,3), new Position(4,3));
        Move validMove = new Move(new Position(6, 0), new Position(5,0));

        adapter.postOnBreakthrough(); // new game

        assertThat(adapter.putOnBreakthrough(invalidMove, "1"), is(false));
        assertThat(adapter.putOnBreakthrough(validMove, "1"), is(true));
    }

    @Test
    public void shouldReturnColorNONForGetWinner(){
        adapter.postOnBreakthrough();
        assertThat(adapter.getOnBreakthoughWinner("1"), is(Color.NONE)); //BreakthroughSurrogate always returns Color.NONE
    }

}