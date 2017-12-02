package starter.ebean.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    private String username;
    private String passwordHash;
    private String branch;
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Cheque> cheques;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBranch() {

        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=\'"+getId()+"\'"+
                "username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", branch=" + branch +
                ", role=" + role +
                ", cheques=" + cheques +
                '}';
    }
}
