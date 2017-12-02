package starter.ebean;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;
import starter.ebean.App;

public class UserManagementTest {
      /**
   * One app/server for all the test of this class. If you want to start/stop a new server per test,
   * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
   */
  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  @Test
  public void itShouldCreateNewUser(){

//      1) User management : create users accepting username, password , branch and role.
  }

  @Test
  public void itShouldModifyUser(){
      //
  }

}
