package starter.ebean;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;
import starter.ebean.App;

public class DisbursementTest {
      /**
   * One app/server for all the test of this class. If you want to start/stop a new server per test,
   * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
   */
  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  @Test
  public void itShouldDisburseCheque(){

  }

//3) Disbursement Request:
// User's with loan officer role should be able to request for cheques to be issued.
// The system must accept the customer's name, customer number , loan amount . These information would be stored on the database.
}
