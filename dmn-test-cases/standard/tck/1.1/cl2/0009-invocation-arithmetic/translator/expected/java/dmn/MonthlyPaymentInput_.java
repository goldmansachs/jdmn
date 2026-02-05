
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "MonthlyPayment"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MonthlyPaymentInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TLoan loan;
    private java.lang.Number fee;

    public MonthlyPaymentInput_() {
    }

    public MonthlyPaymentInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object loan = input_.get("Loan");
            setLoan(type.TLoan.toTLoan(loan));
            Object fee = input_.get("fee");
            setFee((java.lang.Number)fee);
        }
    }

    public type.TLoan getLoan() {
        return this.loan;
    }

    public void setLoan(type.TLoan loan) {
        this.loan = loan;
    }

    public java.lang.Number getFee() {
        return this.fee;
    }

    public void setFee(java.lang.Number fee) {
        this.fee = fee;
    }

}
