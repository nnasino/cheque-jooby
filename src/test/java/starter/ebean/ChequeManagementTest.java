package starter.ebean;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runners.Parameterized;
import starter.ebean.App;
import starter.ebean.models.Cheque;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class ChequeManagementTest {
    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
     */
    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());

    @Parameterized.Parameter
    public Cheque cheque;

    private Cheque mockInvalidCheque() {

    }

    @Parameterized.Parameters
    public static Collection<Cheque> data() {
        List<Cheque> chequeList = new LinkedList<>();
        Cheque invalidStartNumber = new Cheque();
        invalidStartNumber.setBankName("UBA");
        invalidStartNumber.setStartNumber(-1);
        invalidStartNumber.setStartNumber(50);

        Cheque invalidEndNumber = new Cheque();
        invalidEndNumber.setBankName("UBA");
        invalidEndNumber.setStartNumber(20);
        invalidEndNumber.setEndNumber(-200);

        Cheque invalidRange = new Cheque();
        invalidRange.setBankName("UBA");
        invalidRange.setStartNumber(1);
        invalidRange.setEndNumber(500000);

        chequeList.add(invalidEndNumber);
        chequeList.add(invalidStartNumber);
        chequeList.add(invalidRange);

        return chequeList;
    }


    @Test
    public void itShouldCreateAndAssignCheque() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotCreateChequeWithInvalidStartOrEnd() {

    }


    @Test
    public void itShouldNotCreateChequeWithUsedLeaves() {

    }
//            2) Cheque management: Create and assign new cheques.
// To create a cheque you need to accept the bank name, start and end number of the cheque since cheques are serial.
// You are to assign cheques to users who have the branch manager role.
}
