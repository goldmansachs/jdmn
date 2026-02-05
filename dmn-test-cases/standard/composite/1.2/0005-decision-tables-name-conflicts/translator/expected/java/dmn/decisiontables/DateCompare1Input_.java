package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate decisioninputs1_dateD;

    public DateCompare1Input_() {
    }

    public DateCompare1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs1_dateD = input_.get("decisionInputs1.dateD");
            setDecisioninputs1_dateD((java.time.LocalDate)decisioninputs1_dateD);
        }
    }

    public java.time.LocalDate getDecisioninputs1_dateD() {
        return this.decisioninputs1_dateD;
    }

    public void setDecisioninputs1_dateD(java.time.LocalDate decisioninputs1_dateD) {
        this.decisioninputs1_dateD = decisioninputs1_dateD;
    }

}
