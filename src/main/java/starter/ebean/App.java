package starter.ebean;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import io.ebean.config.ServerConfig;
import org.jooby.Jooby;
import org.jooby.ebean.Ebeanby;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.slf4j.Logger;
import starter.ebean.dtos.ResponseMessage;
import starter.ebean.models.Branch;
import starter.ebean.models.User;
import starter.ebean.models.query.QBranch;
import starter.ebean.models.query.QUser;
import starter.ebean.services.ChequeService;
import starter.ebean.services.UserService;

/**
 * Starter project for Ebean ORM.
 */
public class App extends Jooby {

    @Inject
    private ChequeService chequeService;

    @Inject
    private UserService userService;
    {
        use(new Jackson());

        /** Jdbc: */
        use(new Jdbc());

        use(new AppModule());
        /**
         * Configure Ebean:
         */
        use(new Ebeanby().doWith((final ServerConfig ebean) -> {
            /** These can be done in .conf file too: */
            ebean.setDisableClasspathSearch(false);
            ebean.setDdlGenerate(true);
            ebean.setDdlRun(true);
        }));

        /**
         * Insert some data on startup:
         */
        onStart(() -> {
            EbeanServer ebean = require(EbeanServer.class);
       });

        //User API
        /** Add a new user to the system
         *
         */
        post("/users", req -> {
            User user = null;
            Long id = null;
            try {
                user = req.body().to(User.class);
                user.setId(userService.addUser(user));
            } catch (Exception exc) {
                exc.printStackTrace();
                return "{ \"message\": \"An Error Occurred: Invalid Inputs supplied\"}";
            }
            System.out.println(user.toString());
            return user;
        });

        /**
         * Find user by id
         */
        get("/users/{id:\\d+}", req -> {
            Long id = req.param("id").longValue();
            return userService.findUserById(id);
        });


        /**
         * Find all users
         */
        get("/users", req -> {
            //check for page and sort parameters

            //return page

            //return sort

            //return all
            return userService.findAll();
        });



        //Cheque API
        /** Add a new cheque to the system and assign it to a user
         *
         */

    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
