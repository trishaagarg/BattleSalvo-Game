import cs3500.pa04.Driver;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the Driver class.
 */
public class DriverTest {
  @Test
  public void testSample() {
    Driver.main(new String[] {"0.0.0.0", "35001"});
  }
}
