package mini.jooby.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @Column(unique = true)
    private String username;
    private String passwordHash;
    @ManyToOne
    private Branch branch;
    @Enumerated(value = EnumType.STRING)
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
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
