package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate decisioninputs_dateD;

    public DateCompare1Input_() {
    }

    public DateCompare1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs_dateD = input_.get("decisionInputs.dateD");
            setDecisioninputs_dateD((java.time.LocalDate)decisioninputs_dateD);
        }
    }

    public java.time.LocalDate getDecisioninputs_dateD() {
        return this.decisioninputs_dateD;
    }

    public void setDecisioninputs_dateD(java.time.LocalDate decisioninputs_dateD) {
        this.decisioninputs_dateD = decisioninputs_dateD;
    }

}
