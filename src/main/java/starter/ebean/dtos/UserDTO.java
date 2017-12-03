package starter.ebean.dtos;

import org.hibernate.validator.constraints.Length;
import starter.ebean.models.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class UserDTO {

    private Long id;
    @NotEmpty
    @Length(min = 8, max = 40)
    @Pattern(message="Username can only contain letters and numbers", regexp = "^[a-z0-9]{8,40}$")
    private String username;
    @Length(min = 8, max = 40)
    private String password;
    @Length(message = "Branch code is 4 characters", min=4,max=4)
    private String branch; //branch code
    @NotNull
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
