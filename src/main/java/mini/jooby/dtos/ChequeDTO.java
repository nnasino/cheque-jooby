package mini.jooby.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChequeDTO {
    private Long id;
    @NotEmpty
    private String bankName;
    @Min(1)
    private int startNumber;
    @Min(2)
    @Max(888888888)
    private int endNumber;
    @NotNull
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChequeDTO{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", startNumber=" + startNumber +
                ", endNumber=" + endNumber +
                ", userId=" + userId +
                '}';
    }
}
