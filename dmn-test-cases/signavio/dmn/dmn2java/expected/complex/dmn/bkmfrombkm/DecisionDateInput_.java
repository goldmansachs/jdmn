
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionDate"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionDateInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate dateInput;

    public DecisionDateInput_() {
    }

    public DecisionDateInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateInput = input_.get("date input");
            setDateInput((java.time.LocalDate)dateInput);
        }
    }

    public java.time.LocalDate getDateInput() {
        return this.dateInput;
    }

    public void setDateInput(java.time.LocalDate dateInput) {
        this.dateInput = dateInput;
    }

}
