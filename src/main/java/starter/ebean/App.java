package starter.ebean;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import io.ebean.config.ServerConfig;
import org.jooby.Jooby;
import org.jooby.Request;
import org.jooby.ebean.Ebeanby;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.ebean.dtos.BranchDTO;
import starter.ebean.dtos.ChequeDTO;
import starter.ebean.dtos.UserDTO;
import starter.ebean.models.Branch;
import starter.ebean.models.Role;
import starter.ebean.services.BranchService;
import starter.ebean.services.ChequeService;
import starter.ebean.services.UserService;

/**
 * Starter project for Ebean ORM.
 */
public class App extends Jooby {

    private final static Logger logger = LoggerFactory.getLogger(App.class);
    @Inject
    private BranchService branchService;
    @Inject
    private ChequeService chequeService;
    @Inject
    private UserService userService;

    {
        use(new Jackson());

        /** Jdbc: */
        use(new Jdbc());

        /**
         * Configure Ebean:
         */
        use(new Ebeanby().doWith((final ServerConfig ebean) -> {
            /** These can be done in .conf file too: */
            ebean.setDisableClasspathSearch(false);
            ebean.setDdlGenerate(true);
            ebean.setDdlRun(true);
        }));

        use(new AppModule());

        /**
         * Insert some data on startup:
         */
        onStart(() -> {
            EbeanServer ebean = require(EbeanServer.class);
            //Add branches and default admin user

            Branch branch = new Branch();
            branch.setBranchCode("0001");
            branch.setBranchName("Head Office");
            ebean.insert(branch);
            BranchDTO ojuelegba = new BranchDTO("Ojuelegba Road", "0002");
            ebean.insert(branchService.toEntity(ojuelegba));
            BranchDTO johnRoad = new BranchDTO("John Road", "0003");
            ebean.insert(branchService.toEntity(johnRoad));
            BranchDTO marina = new BranchDTO("Marina", "0004");
            ebean.insert(branchService.toEntity(marina));
            BranchDTO ajose = new BranchDTO("Ajose Adeogun", "0005");
            ebean.insert(branchService.toEntity(ajose));


            UserDTO userDTO = new UserDTO();
            userDTO.setBranch(branch.getBranchCode());
            userDTO.setPassword("password123");
            userDTO.setUsername("administrator");
            userDTO.setRole(Role.ADMIN);
            userService.addUser(userDTO);

        });

        //User API
        /** Add a new user to the system
         *
         */
        post("/users", req -> {
            UserDTO user = null;
            try {
                user = req.body().to(UserDTO.class);
                user.setId(userService.addUser(user));
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: username, password, branch, role \"}";
            }
            logger.info(user.toString());
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
         * Find all users. can also take page and pageSize to control
         * records returned. Returns first 10 records by default
         */
        get("/users", req -> {
            int page = 1;
            int pageSize = 10;

            //check for page
            try {
                page = req.param("page").intValue();
            } catch (Exception exc) {
                logger.error("Unable to parse page param:" + exc.getMessage());
            }

            //check for pageSize
            try {
                page = req.param("pageSize").intValue();
            } catch (Exception exc) {
                logger.error("Unable to parse pageSize param:" + exc.getMessage());
            }

            return userService.findPage(page, pageSize);
        });


        //Cheque API
        post("/cheques", req -> {
            ChequeDTO chequeDTO = null;
            try {
                chequeDTO = req.body().to(ChequeDTO.class);
                chequeDTO.setId(chequeService.addCheque(chequeDTO));
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: bankName, endNumber, startNumber, userId \"}";
            }
            logger.info(chequeDTO.toString());
            return chequeDTO;
        });

        get("/cheques", req -> {
            int page = getPageParam(req);
            int pageSize = getPageSizeParam(req);

            return chequeService.findPage(page, pageSize);
        });


        get("/cheques/{id:\\d+}", req -> {
            Long id = req.param("id").longValue();
            return chequeService.findChequeById(id);
        });
    }

    public static int getPageParam(Request req) {
        int page = 1;
        try {
            page = req.param("page").intValue();
        } catch (Exception exc) {
            logger.error("Unable to parse page param:" + exc.getMessage());
        }
        return page;
    }

    public static int getPageSizeParam(Request req) {
        int pageSize = 10;
        try {
            pageSize = req.param("pageSize").intValue();
        } catch (Exception exc) {
            logger.error("Unable to parse pageSize param:" + exc.getMessage());
        }
        return pageSize;
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
