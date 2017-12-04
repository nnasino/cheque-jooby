package mini.jooby;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import io.ebean.config.ServerConfig;
import mini.jooby.dtos.BranchDTO;
import mini.jooby.dtos.ChequeDTO;
import mini.jooby.dtos.LoanDTO;
import mini.jooby.dtos.UserDTO;
import mini.jooby.models.Branch;
import mini.jooby.models.Role;
import mini.jooby.models.User;
import mini.jooby.services.*;
import mini.jooby.services.implementations.SecurityServiceImpl;
import org.jooby.Jooby;
import org.jooby.Request;
import org.jooby.ebean.Ebeanby;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.jooby.pac4j.Auth;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mini project assignment.
 */
public class App extends Jooby {

    private final static Logger logger = LoggerFactory.getLogger(App.class);
    @Inject
    private BranchService branchService;
    @Inject
    private ChequeService chequeService;
    @Inject
    private UserService userService;
    @Inject
    private SecurityService securityService;

    @Inject
    private LoanService loanService;

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
        use(new Auth().basic("*", SecurityServiceImpl.class));

        /**
         * Insert some data on startup:
         */
        onStart(() -> {
            EbeanServer ebean = require(EbeanServer.class);
            //Add branches and default admin user

            if(ebean.find(Branch.class).findCount() <= 0) {
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
            }


            if(ebean.find(User.class).findCount() <= 0) {
                User user = new User();
                user.setPasswordHash(securityService.createPassword("password123"));
                user.setUsername("administrator");
                user.setRole(Role.ADMIN);
                ebean.insert(user);

                user = new User();
                user.setPasswordHash(securityService.createPassword("password123"));
                user.setUsername("john.bola");
                user.setRole(Role.LOAN_OFFICER);
                ebean.insert(user);

                user = new User();
                user.setPasswordHash(securityService.createPassword("password123"));
                user.setUsername("daniel.wale");
                user.setRole(Role.BRANCH_MANAGER);
                ebean.insert(user);
            }


        });

        //User API
        /** Add a new user to the system
         *
         */
        post("/users", req -> {
            CommonProfile commonProfile = require(CommonProfile.class);
            logger.info("{}", commonProfile.getUsername());
            UserDTO user = null;
            try {
                user = req.body().to(UserDTO.class);
                return userService.addUser(user, getLoggedInUser());
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: username, password, branch, role \"}";
            }
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
        /** Add and assign cheque
         *
         */
        post("/cheques", req -> {
            ChequeDTO chequeDTO = null;
            try {
                chequeDTO = req.body().to(ChequeDTO.class);
                return chequeService.addCheque(chequeDTO, getLoggedInUser());
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: bankName, endNumber, startNumber, userId \"}";
            }
        });

        /**
         * Find all cheques. can also take page and pageSize to control
         * records returned. Returns first 10 records by default
         */
        get("/cheques", req -> {
            int page = getPageParam(req);
            int pageSize = getPageSizeParam(req);

            return chequeService.findPage(page, pageSize);
        });

        /**
         * Find cheque by id
         */
        get("/cheques/{id:\\d+}", req -> {
            Long id = req.param("id").longValue();
            return chequeService.findChequeById(id);
        });


        //Loan disbursement API
        /** Add and assign cheque
         *
         */
        post("/loans", req -> {
            LoanDTO loanDTO = null;
            try {
                loanDTO = req.body().to(LoanDTO.class);
                return loanService.addLoan(loanDTO, getLoggedInUser());
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: customerName, " +
                        "customerNumber, loanAmount \"}";
            }
        });

        /**
         * Find loan by id
         */
        get("/loans/{id:\\d+}", req -> {
            Long id = req.param("id").longValue();
            return loanService.findLoanById(id);
        });

        /**
         * Find all Loans. can also take page and pageSize to control
         * records returned. Returns first 10 records by default
         */
        get("/loans", req -> {
            int page = getPageParam(req);
            int pageSize = getPageSizeParam(req);

            return loanService.findPage(page, pageSize);
        });


        //Branch api
        get("/branches", req -> {
            return branchService.findAll();
        });

        post("/branches", req -> {
BranchDTO branchDTO = null;
            try {
                branchDTO = req.body().to(BranchDTO.class);
                return branchService.addBranch(branchDTO, getLoggedInUser());
            } catch (IllegalArgumentException exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: " + exc.getMessage() + " \"}";
            } catch (Exception exc) {
                logger.error("Error: {}", exc.getMessage());
                return "{ \"message\": \"An Error Occurred: Invalid inputs supplied. Expected fields: branchName, " +
                        "branchCode\"}";
            }
        });

    }

    private User getLoggedInUser(){
        CommonProfile commonProfile = require(CommonProfile.class);
        return userService.findUserById(Long.parseLong(commonProfile.getId()));
    }

    public static int getPageParam(Request req) {
        int page = 1;
        try {
            page = req.param("page").intValue();
        } catch (Exception exc) {
        }
        return page;
    }

    public static int getPageSizeParam(Request req) {
        int pageSize = 10;
        try {
            pageSize = req.param("pageSize").intValue();
        } catch (Exception exc) {
        }
        return pageSize;
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
