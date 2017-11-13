package breakthrough.ui;

import breakthrough.domain.Breakthrough;
import breakthrough.domain.BreakthroughSurrogate;
import frs.broker.ClientRequestHandler;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/** Test the CLI UI.
 *
 * Created by Henrik Baerbak Christensen, AU.
 */
public class TestClientInterpreter {
  private ClientInterpreter cli;
  private ByteArrayOutputStream baos;
  private PrintStream ps;
  private Breakthrough game;

  @Before
  public void setup() {
    baos = new ByteArrayOutputStream();
    ps = new PrintStream(baos);

    game = new BreakthroughSurrogate();
  }

  /* A 'smoke test' of the CLI behaviour, that
  is a test that test that things 'seems' to be
  working.
   */
  @Test
  public void shouldSmokeTestCLIBehavior() {

    /* Simulate that the user types 'h', 'p',
    next 'm 6 1 5 2', next 'm 6 1 5 2',
    'p' again, and finally 'q'
     */
    String commandList = "h\np\nm 6 1 5 2\nm 6 6 1 1\np\nq\n";

    cli = new ClientInterpreter(game, makeToInputStream(commandList), ps);
    cli.readEvalLoop();
    String output = baos.toString();

    // Enable the output below to see the CLI output
    // System.out.println("--> "+output);

    // Assert that we see the help command
    assertThat(output, containsString("=== Help on the Breakthrough commands. ==="));

    // Assert that we have a board in the output for the initial p
    assertThat(output, containsString("6: W W W W W W W W"));
    assertThat(output, containsString("Next Player: WHITE"));

    // Assert that the move was printed and deemed valid
    assertThat(output, containsString("Move pawn (6,1) to (5,2) is VALID"));

    // Assert that the move was printed and deemed valid
    assertThat(output, containsString("Move pawn (6,6) to (1,1) is INVALID"));

    // And assert that the board is updated for the second 'p'
    assertThat(output, containsString("6: W - W W W W W W"));
    assertThat(output, containsString("5: - - W - - - - -"));

  }

  @Test
  public void shouldSmokeTestUnhappyCLIBehavior() {
    /* Simulate that the user types 'x',
    next 'm 6 1 a 2',  next 'm 6 1 5', and finally 'q'
     */
    String commandList = "x\nm 6 1 a 2\nm 6 1 5\nq\n";

    cli = new ClientInterpreter(game, makeToInputStream(commandList), ps);
    cli.readEvalLoop();
    String output = baos.toString();

    // Assert that 'x' is not understood
    assertThat(output, containsString("I do not understand that command. (Type 'h' for help)"));

    // Assert that number parsing failed on 'm 6 1 a 2'
    assertThat(output, containsString("One or more parameters are not numbers."));

    // Assert that number parsing failed on 'm 6 1 5'
    assertThat(output, containsString("The format of the move command was wrong. (Type 'h' for help)"));
  }

    /** Convert a string consisting of linefeed separated commands
     * into an input stream.
     * @param commandString the string of commands
     * @return a byte array version of it.
     */
  private InputStream makeToInputStream(String commandString) {
    return new ByteArrayInputStream(commandString.getBytes());
  }

}
