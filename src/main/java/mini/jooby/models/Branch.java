package mini.jooby.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Branch extends AbstractEntity {
    private String branchName;
    @Column(unique = true)
    private String branchCode;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                '}';
    }
}
