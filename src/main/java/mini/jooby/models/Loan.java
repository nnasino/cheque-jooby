package mini.jooby.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Loan extends AbstractEntity{

    @ManyToOne
    private Customer customer;
    private BigDecimal loanAmount;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "customer=" + customer +
                ", loanAmount=" + loanAmount +
                ", addedBy=" + getAddedBy() +
                '}';
    }
}
//    Disbursement Request: User's with loan officer role should be able to request for cheques to be issued.
//        The system must accept the customer's name, customer number ,
//        loan amount . These information would be stored on the database.