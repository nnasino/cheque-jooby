package starter.ebean.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BranchDTO {

    @NotEmpty
    private String branchName;
    @Size(min = 4 , max = 4)
    private String branchCode;

    public BranchDTO(){}

    public BranchDTO(String branchName, String branchCode){
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
