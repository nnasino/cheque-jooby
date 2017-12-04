package mini.jooby.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import mini.jooby.dtos.ChequeDTO;
import mini.jooby.dtos.ResponseMessage;
import mini.jooby.models.Cheque;
import mini.jooby.models.Role;
import mini.jooby.models.User;
import mini.jooby.services.ChequeService;
import mini.jooby.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class ChequeServiceImpl implements ChequeService{

    private final static int MAX_CHEQUE_LEAVES = 200;

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    @Inject
    private EbeanServer ebean;

    @Inject
    private UserService userService;


    private void validateCheque(ChequeDTO chequeDTO) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ChequeDTO>> constraintViolations =
                validator.validate(chequeDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<ChequeDTO> error : constraintViolations) {
                logger.error(error.getPropertyPath() + "::" + error.getMessage());
                builder.append(error.getPropertyPath() + "::" + error.getMessage());
            }
            throw new IllegalArgumentException(builder.toString());
        }

        //confirm that the specified user exists and has loan officer role
        User user = userService.findUserById(chequeDTO.getUserId());

        if(user == null){
            throw new IllegalArgumentException("User was not found");
        }

        if(user.getRole() != Role.BRANCH_MANAGER){
            throw new IllegalArgumentException("Specified User does not have the BRANCH_MANAGER role");
        }

        //check range shouldn't be more than 200
        int chequeLeaves = chequeDTO.getEndNumber() - chequeDTO.getStartNumber();
        if(chequeLeaves < 0 || chequeLeaves > MAX_CHEQUE_LEAVES){
            throw new IllegalArgumentException("Invalid start and end number. " +
                    "Number of leaves can't be less than 1 or more than 200");
        }

        //check if cheques leaves are in use
        List<Cheque> cheques = ebean.find(Cheque.class).where()
                .eq("user_id", chequeDTO.getUserId())
                .eq("bankName", chequeDTO.getBankName())
                .findList();

        logger.info("cheques: " + cheques.size());
        for(Cheque cheque: cheques){

            int x1 = chequeDTO.getStartNumber();
            int x2 = chequeDTO.getEndNumber();
            int y1 = cheque.getStartNumber();
            int y2 = cheque.getEndNumber();

            if((x1 >= y1 && x1 <= y2)||
                    (x2 >= y1 && x2 <= y2) ||
                    (y1 >= x1 && y1 <= x2) ||
                    (y2 >= x1 && y2 <= x2)){
                //cheque leaves already taken
                throw new IllegalArgumentException("Some of the cheque leaves are in use") ;
            }
       }

    }

    private Cheque toEntity(ChequeDTO chequeDTO){
        Cheque cheque = new Cheque();
        cheque.setBankName(chequeDTO.getBankName());
        cheque.setStartNumber(chequeDTO.getStartNumber());
        cheque.setEndNumber(chequeDTO.getEndNumber());
        return cheque;
    }

    @Override
    public Long addCheque(ChequeDTO chequeDTO, User addedBy) {
        validateCheque(chequeDTO);
        User user = userService.findUserById(chequeDTO.getUserId());
        Cheque cheque = toEntity(chequeDTO);
        cheque.setAddedBy(addedBy);
        cheque.setUser(user);
        ebean.insert(cheque);
        logger.info("Successfully added: {}", cheque.toString());
        return cheque.getId();
    }

    @Override
    public Cheque findChequeById(Long id){
        return ebean.find(Cheque.class, id);
    }

    @Override
    public List<Cheque> findPage(int page, int pageSize){
        return ebean.find(Cheque.class)
                .setMaxRows(pageSize)
                .setFirstRow((page * pageSize) - pageSize)
                .findList();
    }

    @Override
    public ResponseMessage assignChequeToUser(Long chequeId, Long userId){
        Cheque cheque = ebean.find(Cheque.class, chequeId);
        User user = ebean.find(User.class, userId);
        cheque.setUser(user);
        ebean.save(user);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        responseMessage.setMessage("Successfully assigned cheque to user");
        return responseMessage;
    }
}
