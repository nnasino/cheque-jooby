package mini.jooby.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import mini.jooby.dtos.CustomerDTO;
import mini.jooby.models.Customer;
import mini.jooby.models.User;
import mini.jooby.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CustomerServiceImpl implements CustomerService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Inject
    private EbeanServer ebean;

    private Customer toEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setCustomerNumber(customerDTO.getCustomerNumber());
        customer.setCustomerName(customerDTO.getCustomerName());
        return customer;
    }

    private void validateCustomer(CustomerDTO customerDTO){
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CustomerDTO>> constraintViolations =
                validator.validate(customerDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<CustomerDTO> error : constraintViolations) {
                logger.error(error.getPropertyPath() + "::" + error.getMessage());
                builder.append(error.getPropertyPath() + "::" + error.getMessage());
            }
            throw new IllegalArgumentException(builder.toString());
        }

        //check if customer already exists
        Customer customer = findCustomerByCustomerNumber(customerDTO.getCustomerNumber());

        if(customer != null){
            throw new IllegalArgumentException("Customer already exists");
        }
    }

    @Override
    public Long addCustomer(CustomerDTO customerDTO, User addedBy) {
        validateCustomer(customerDTO);
        Customer customer = toEntity(customerDTO);
        customer.setAddedBy(addedBy);
        ebean.insert(customer);
        logger.info("Successfully added: {}", customer.toString());
        return customer.getId();
    }

    @Override
    public Customer findCustomerById(Long id) {
        return ebean.find(Customer.class, id);
    }

    @Override
    public Customer findCustomerByCustomerNumber(String customerNumber) {
        return ebean.find(Customer.class)
            .where()
            .eq("customerNumber", customerNumber)
            .findUnique();
    }
}
