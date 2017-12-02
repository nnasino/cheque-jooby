package starter.ebean.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cheque extends AbstractEntity{
    private String bankName;
    private int startNumber;
    private int endNumber;

    @ManyToOne
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

    @Override
    public String toString() {
        return "Cheque{" +
                "bankName='" + bankName + '\'' +
                ", startNumber=" + startNumber +
                ", endNumber=" + endNumber +
                '}';
    }
}
