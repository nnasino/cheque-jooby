package mini.jooby.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cheque extends AbstractEntity{
    private String bankName;
    private int startNumber;
    private int endNumber;

    @ManyToOne
    @JsonIgnore
    private User user;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "bankName='" + bankName + '\'' +
                ", startNumber=" + startNumber +
                ", endNumber=" + endNumber +
                '}';
    }
}
