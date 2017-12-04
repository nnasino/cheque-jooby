package mini.jooby.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends AbstractEntity{
    private String customerName;
    @Column(unique = true)
    private String customerNumber;

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

    @Override
    public String toString() {
        return "Customer{" +
                "customerName='" + customerName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                '}';
    }
}
