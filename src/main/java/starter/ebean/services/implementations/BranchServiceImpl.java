package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.ebean.dtos.BranchDTO;
import starter.ebean.dtos.CustomerDTO;
import starter.ebean.models.Branch;
import starter.ebean.models.User;
import starter.ebean.services.BranchService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class BranchServiceImpl implements BranchService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Inject
    private EbeanServer ebean;

    @Override
    public void mockBranch() {

        Branch branch = new Branch();
        branch.setBranchCode("0001");
        branch.setBranchName("Head Office");
        ebean.insert(branch);
    }

    private void validateBranch(BranchDTO branchDTO){
                Validator validator = factory.getValidator();
        Set<ConstraintViolation<BranchDTO>> constraintViolations =
                validator.validate(branchDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<BranchDTO> error : constraintViolations) {
                logger.error(error.getPropertyPath() + "::" + error.getMessage());
                builder.append(error.getPropertyPath() + "::" + error.getMessage());
            }
            throw new IllegalArgumentException(builder.toString());
        }

    }
    @Override
    public Branch toEntity(BranchDTO branchDTO) {
        Branch branch = new Branch();
        branch.setBranchName(branchDTO.getBranchName());
        branch.setBranchCode(branchDTO.getBranchCode());
        return branch;
    }

    private boolean branchExists(Branch branch) {
        int count = ebean.find(Branch.class)
                .where()
                .eq("branchCode", branch.getBranchCode())
                .findCount();

        return count > 0;
    }

    @Override
    public Long addBranch(BranchDTO branchDTO, User addedBy) {
        validateBranch(branchDTO);
        Branch branch = toEntity(branchDTO);
        branch.setAddedBy(addedBy);

        if (branchExists(branch)) {
            throw new IllegalArgumentException("The specified branch already exists. Check branch code");
        }

        ebean.insert(branch);
        logger.info("Successfully added: {}", branch.toString());
        return branch.getId();
    }

    @Override
    public Branch findBranchById(Long id) {
        return ebean.find(Branch.class, id);
    }

    @Override
    public Branch findBranchByCode(String code) {
        return ebean.find(Branch.class)
                .where()
                .eq("branchCode", code)
                .findUnique();
    }

    @Override
    public List<Branch> findAll() {
        return ebean.find(Branch.class)
                .findList();
    }
}
