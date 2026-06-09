
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "payment"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PaymentInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TLoan loan;

    public PaymentInput_() {
    }

    public PaymentInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object loan = input_.get("http://www.trisotech.com/definitions/_1fedf2c0-0f4a-470c-bc66-a15528e8a49a#loan");
            setLoan(type.TLoan.toTLoan(loan));
        }
    }

    public type.TLoan getLoan() {
        return this.loan;
    }

    public void setLoan(type.TLoan loan) {
        this.loan = loan;
    }

}
