package mini.jooby.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import mini.jooby.dtos.CustomerDTO;
import mini.jooby.dtos.LoanDTO;
import mini.jooby.models.Customer;
import mini.jooby.models.Loan;
import mini.jooby.models.Role;
import mini.jooby.models.User;
import mini.jooby.services.CustomerService;
import mini.jooby.services.LoanService;
import mini.jooby.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class LoanServiceImpl implements LoanService {


    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Inject
    private EbeanServer ebean;

    @Inject
    private UserService userService;

    @Inject
    private CustomerService customerService;

    private Loan toEntity(LoanDTO loanDTO){
        Loan loan = new Loan();
        loan.setLoanAmount(loanDTO.getLoanAmount());
        return loan;
    }


    private void validateLoan(LoanDTO loanDTO) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoanDTO>> constraintViolations =
                validator.validate(loanDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<LoanDTO> error : constraintViolations) {
                logger.error(error.getPropertyPath() + "::" + error.getMessage());
                builder.append(error.getPropertyPath() + "::" + error.getMessage());
            }
            throw new IllegalArgumentException(builder.toString());
        }


    }

    @Override
    public Loan addLoan(LoanDTO loanDTO, User addedBy) {
        validateLoan(loanDTO);

        logger.info(addedBy.toString());
        if(addedBy.getRole() != Role.LOAN_OFFICER){
            throw new IllegalArgumentException("Insufficient Privileges: You do not have the LOAN OFFICER role");
        }

        Loan loan = toEntity(loanDTO);

        Customer customer = customerService.findCustomerByCustomerNumber(loanDTO.getCustomerNumber());

        if(customer == null){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerName(loanDTO.getCustomerName());
            customerDTO.setCustomerNumber(loanDTO.getCustomerNumber());
            customer = customerService.addCustomer(customerDTO, addedBy);
        }

        loan.setCustomer(customer);
        loan.setAddedBy(addedBy);
        ebean.insert(loan);
        logger.info("Successfully added: {}", loan.toString());
        return loan;
    }

    @Override
    public Loan findLoanById(Long id) {
        return ebean.find(Loan.class, id);
    }

    @Override
    public List<Loan> findPage(int page, int pageSize) {
        return ebean.find(Loan.class)
                .setMaxRows(pageSize)
                .setFirstRow((page * pageSize) - pageSize)
                .findList();
    }

    @Override
    public List<Loan> findLoansForUser(Long userId) {
        return ebean.find(Loan.class)
                .where()
                .eq("user_id", userId)
                .findList();
    }
}
