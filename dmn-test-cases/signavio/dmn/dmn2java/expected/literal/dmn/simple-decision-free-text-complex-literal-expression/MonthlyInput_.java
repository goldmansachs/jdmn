
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "monthly"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MonthlyInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Loan loan;

    public MonthlyInput_() {
    }

    public MonthlyInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object loan = input_.get("http://www.provider.com/dmn/1.1/diagram/701a4e318a6d4d4f823018c5feaa8f81.xml#loan");
            setLoan(type.Loan.toLoan(null));
        }
    }

    public type.Loan getLoan() {
        return this.loan;
    }

    public void setLoan(type.Loan loan) {
        this.loan = loan;
    }

}
