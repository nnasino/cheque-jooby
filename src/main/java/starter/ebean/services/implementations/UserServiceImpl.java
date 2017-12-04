package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.ebean.dtos.UserDTO;
import starter.ebean.models.User;
import starter.ebean.models.query.QUser;
import starter.ebean.services.BranchService;
import starter.ebean.services.SecurityService;
import starter.ebean.services.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    @Inject
    private EbeanServer ebean;
    @Inject
    private SecurityService securityService;
    @Inject
    private BranchService branchService;

    private boolean userExists(String username) {
        return ebean.find(User.class)
                .where()
                .eq("username", username)
                .findCount() > 0;
    }

    private String encryptPassword(String plainPassword) {
        //TODO encrypt
        return plainPassword;
    }

    private void validateUser(UserDTO userDTO) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<UserDTO> error : constraintViolations) {
                logger.error(error.getPropertyPath() + "::" + error.getMessage());
                builder.append(error.getPropertyPath() + "::" + error.getMessage());
            }
            throw new IllegalArgumentException(builder.toString());
        }
    }

    private User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(securityService.createPassword(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setBranch(branchService.findBranchByCode(userDTO.getBranch()));
        return user;
    }

    @Override
    public Long addUser(UserDTO userDTO) {
        validateUser(userDTO);
        User user = toEntity(userDTO);

        if (user.getBranch() == null) {
            throw new IllegalArgumentException("Specified branch not found");
        }

        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("Specified user already exists");
        }

        ebean.insert(user);
        logger.info(user.toString());
        return user.getId();
    }

    @Override
    public User findUserById(Long id) {
        return ebean.find(User.class, id);
    }

    @Override
    public List<User> findPage(int page, int pageSize) {
        return ebean.find(User.class)
                .setMaxRows(pageSize)
                .setFirstRow((page * pageSize) - pageSize+ 1)
                .findList();
    }
}
