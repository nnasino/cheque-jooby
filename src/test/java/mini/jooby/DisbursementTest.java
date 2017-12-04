package mini.jooby;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import mini.jooby.dtos.LoanDTO;
import mini.jooby.dtos.UserDTO;
import org.jooby.test.JoobyRule;
import org.junit.*;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class DisbursementTest {
    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
     */
    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());
    private UserDTO adminUser;
    private UserDTO loanOfficer;

    private LoanDTO validLoan;
    private LoanDTO invalidLoan;

    private static String endPoint = "loans";

    @Before
    public void setUp() {
        validLoan = new LoanDTO();
        validLoan.setCustomerName("Chigozirim Torti");
        validLoan.setCustomerNumber("0021424028");
        validLoan.setLoanAmount(BigDecimal.valueOf(25000000));

        invalidLoan = new LoanDTO();
        invalidLoan.setCustomerName("Chigozirim Torti");
        invalidLoan.setCustomerNumber("0021424028");
        invalidLoan.setLoanAmount(BigDecimal.valueOf(-25000000));


        adminUser = new UserDTO();
        loanOfficer = new UserDTO();

        adminUser.setPassword("password123");
        adminUser.setUsername("administrator");

        loanOfficer.setPassword("password123");
        loanOfficer.setUsername("john.bola");
    }

    @Test
    public void itShouldDisburseLoan() throws JsonProcessingException {
        String response = postAddLoan(validLoan, loanOfficer);
        Assert.assertTrue("It should be sucessful", response.contains("amount"));
        Assert.assertTrue("It should be sucessful", response.contains("amount"));
    }

    @Test
    public void itShouldNotDisburseLoanIfNotLoanOfficer() throws JsonProcessingException {
        String response = postAddLoan(validLoan, adminUser);
        Assert.assertTrue("It should say you don't have loan officer role", response.contains("LOAN OFFICER"));

    }

    @Test
    public void itShouldNotDisburseLoanIfAmountIsInvalid() throws JsonProcessingException {
       String response = postAddLoan(invalidLoan, loanOfficer);
       Assert.assertTrue("It should give an error", response.contains("message"));
    }

    private String postAddLoan(LoanDTO loanDTO, UserDTO userDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        String json = mapper.writeValueAsString(loanDTO);

        Response r = given()
                .contentType("application/json")
                .auth().basic(userDTO.getUsername(), userDTO.getPassword()).
                        body(json).
                        when().
                        post("/"+ endPoint);

        String body = r.getBody().asString();
        System.out.println(body);

        return body;
    }
//3) Disbursement Request:
// User's with loan officer role should be able to request for cheques to be issued.
// The system must accept the customer's name, customer number , loan amount . These information would be stored on the database.
}
