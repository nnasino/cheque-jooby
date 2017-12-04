package mini.jooby.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class LoanDTO {
    private Long id;
    @NotEmpty
    private String customerName;
    @Length(min = 10)
    private String customerNumber;
    @Min(1)
    private BigDecimal loanAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public String toString() {
        return "LoanDTO{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", loanAmount=" + loanAmount +
                '}';
    }
}
