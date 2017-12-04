package mini.jooby;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import mini.jooby.dtos.UserDTO;
import mini.jooby.models.Cheque;
import org.jooby.test.JoobyRule;
import org.junit.*;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;


public class ChequeManagementTest {
    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
     */
    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());

    @Parameterized.Parameter
    public Cheque invalidCheque;

    public Cheque validCheque;

    private UserDTO adminUser;
    private UserDTO loanOfficer;
    private UserDTO branchManager;


    @Before
    public void setUp(){
        validCheque = new Cheque();
        validCheque.setStartNumber(1);
        validCheque.setStartNumber(50);
        validCheque.setBankName("UBA");

        adminUser = new UserDTO();
        loanOfficer = new UserDTO();
        branchManager = new UserDTO();

        adminUser.setPassword("password123");
        adminUser.setUsername("administrator");

        loanOfficer.setPassword("password123");
        loanOfficer.setUsername("john.bola");

        branchManager.setPassword("password123");
        branchManager.setUsername("daniel.wale");
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
    public void itShouldCreateAndAssignCheque() throws JsonProcessingException {
        String response = postAddCheque(validCheque, branchManager);
        assertTrue("Success should be true and id should be return", response.contains("\"id\"") && response.contains("\"success\": true"));
    }

    @Test
    public void itShouldNotCreateChequeWithInvalidStartOrEnd() throws JsonProcessingException {
        String response = postAddCheque(invalidCheque, branchManager);
        assertTrue("Success should be false because of invalid input", response.contains("\"success\": false"));
    }

    @Test
    public void itShouldNotCreateChequeWithUsedLeaves() throws JsonProcessingException {
        itShouldCreateAndAssignCheque();
        String response = postAddCheque(validCheque, branchManager);
        Assert.assertTrue("Success should be false and message should say cheque leaves already in use",
               response.contains("Cheque Leaves in use") && response.contains("\"success\":false"));
    }

    @Test
    public void itShouldNotCreateChequeIfNotBranchManager() throws JsonProcessingException {
        String response = postAddCheque(validCheque, adminUser);
        Assert.assertTrue("It should say you do not have branch manager role", response.contains("BRANCH MANAGER"));
    }


    private String postAddCheque(Cheque cheque, UserDTO userDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        String json = mapper.writeValueAsString(cheque);

        Response r = given()
                .contentType("application/json")
                .auth().basic(userDTO.getUsername(), userDTO.getPassword()).
                        body(json).
                        when().
                        post("/cheques");

        String body = r.getBody().asString();
        System.out.println(body);

        return body;
    }
    //            2) Cheque management: Create and assign new cheques.
    // To create a cheque you need to accept the bank name, start and end number of the cheque since cheques are serial.
    // You are to assign cheques to users who have the branch manager role.
}
