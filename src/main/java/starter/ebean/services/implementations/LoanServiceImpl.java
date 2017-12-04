package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.ebean.dtos.ChequeDTO;
import starter.ebean.dtos.CustomerDTO;
import starter.ebean.dtos.LoanDTO;
import starter.ebean.models.Customer;
import starter.ebean.models.Loan;
import starter.ebean.models.Role;
import starter.ebean.models.User;
import starter.ebean.services.CustomerService;
import starter.ebean.services.LoanService;
import starter.ebean.services.UserService;

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
        loan.setAddedBy(userService.findUserById(loanDTO.getUserId()));
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

        //confirm that the specified user exists and has loan officer role
        User user = userService.findUserById(loanDTO.getUserId());

        if(user == null){
            throw new IllegalArgumentException("User was not found");
        }

        if(user.getRole() != Role.LOAN_OFFICER){
            throw new IllegalArgumentException("You do not have the LOAN OFFICER role");
        }

    }

    @Override
    public Long addLoan(LoanDTO loanDTO) {
        validateLoan(loanDTO);
        Loan loan = toEntity(loanDTO);
        Customer customer = customerService.findCustomerByCustomerNumber(loanDTO.getCustomerNumber());

        if(customer == null){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerName(loanDTO.getCustomerName());
            customerDTO.setCustomerNumber(loanDTO.getCustomerNumber());
            Long id = customerService.addCustomer(customerDTO);
            customer = customerService.findCustomerById(id);
        }

        loan.setCustomer(customer);
        ebean.insert(loan);
        return loan.getId();
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
