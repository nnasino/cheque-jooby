package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import starter.ebean.dtos.BranchDTO;
import starter.ebean.dtos.ResponseMessage;
import starter.ebean.models.Branch;
import starter.ebean.services.BranchService;

import java.util.List;

public class BranchServiceImpl implements BranchService {
    @Inject
    private EbeanServer ebean;

    @Override
    public void mockBranch() {

Branch branch = new Branch();
            branch.setBranchCode("0001");
            branch.setBranchName("Head Office");
            ebean.insert(branch);
            System.out.println(branch.toString());    }

    @Override
    public Branch toEntity(BranchDTO branchDTO){
        Branch branch = new Branch();
        branch.setBranchName(branchDTO.getBranchName());
        branch.setBranchCode(branchDTO.getBranchCode());
        return branch;
    }

    private boolean branchExists(Branch branch){
        int count = ebean.find(Branch.class)
                .where()
                .eq("branchCode", branch.getBranchCode())
                .findCount();

        return count > 0;
    }

    @Override
    public Long addBranch(BranchDTO branchDTO) {
        Branch branch = toEntity(branchDTO);

        if(branchExists(branch)){
            throw new IllegalArgumentException("The specified branch already exists. Check branch code");
        }

        ebean.insert(branch);
        return branch.getId();
    }

    @Override
    public Branch findBranchById(Long id) {
        return null;
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
        return null;
    }

    @Override
    public ResponseMessage assignBranchToUser(Long BranchId, Long userId) {
        return null;
    }
}
