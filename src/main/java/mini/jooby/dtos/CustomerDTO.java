package mini.jooby.dtos;

public class CustomerDTO {

    private String customerName;
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
        return "CustomerDTO{" +
                "customerName='" + customerName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                '}';
    }
}
