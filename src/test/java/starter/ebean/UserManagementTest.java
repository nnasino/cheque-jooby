package starter.ebean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.jooby.test.JoobyRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Parameterized;
import starter.ebean.App;
import starter.ebean.dtos.UserDTO;
import starter.ebean.models.Cheque;
import starter.ebean.models.Role;
import starter.ebean.models.User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class UserManagementTest {
    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
     */
    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());
    @Parameterized.Parameter
    public UserDTO invalidUser;

    public UserDTO validUser;

    @Parameterized.Parameters
    public static Collection<UserDTO> data() {
        List<UserDTO> userList = new LinkedList<>();
        UserDTO invalidUsernameCharacters = new UserDTO();
        invalidUsernameCharacters.setUsername("jkuasdfasdser#$");
        invalidUsernameCharacters.setBranch("0001");
        invalidUsernameCharacters.setRole(Role.ADMIN);
        invalidUsernameCharacters.setPassword("password123");

        UserDTO invalidUsernameLength = new UserDTO();
        invalidUsernameLength.setUsername("johnny");
        invalidUsernameLength.setBranch("0002");
        invalidUsernameLength.setRole(Role.ADMIN);
        invalidUsernameLength.setPassword("password123");


        UserDTO invalidPassword = new UserDTO();
        invalidPassword.setUsername("jonny");
        invalidPassword.setBranch("0003");
        invalidPassword.setRole(Role.ADMIN);
        invalidPassword.setPassword("12");

        userList.add(invalidUsernameCharacters);
        userList.add(invalidPassword);

        return userList;
    }

    @Test
    public void itShouldCreateNewUser() throws JsonProcessingException {
        String response = postAddUser(validUser);
        assertTrue("Success should be false because of invalid input", response.contains("\"success\": false"));
    }

    @Test
    public void itShouldNotCreateDuplicateUsers() throws JsonProcessingException {
        itShouldCreateNewUser();
        String response = postAddUser(validUser);
        Assert.assertTrue("Success should be false and message should say Username is in use",
                response.contains("Username in use") && response.contains("\"success\":false"));
    }

    @Test
    public void itShouldNotCreateInvalidUser() throws JsonProcessingException {
        String response = postAddUser(invalidUser);
        assertTrue("Success should be false because of invalid input", response.contains("An Error Occurred"));
    }

    private String postAddUser(UserDTO user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        String json = mapper.writeValueAsString(user);

        Response r = given()
                .contentType("application/json").
                        body(json).
                        when().
                        post("/user");

        String body = r.getBody().asString();
        System.out.println(body);

        return body;
    }

//      1) User management : create users accepting username, password , branch and role.
}
