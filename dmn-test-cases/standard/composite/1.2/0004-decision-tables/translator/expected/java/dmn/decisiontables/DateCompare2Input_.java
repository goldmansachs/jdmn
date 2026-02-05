package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate decisioninputs_dateD;
    private java.time.LocalDate decisioninputs_dateE;

    public DateCompare2Input_() {
    }

    public DateCompare2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs_dateD = input_.get("decisionInputs.dateD");
            setDecisioninputs_dateD((java.time.LocalDate)decisioninputs_dateD);
            Object decisioninputs_dateE = input_.get("decisionInputs.dateE");
            setDecisioninputs_dateE((java.time.LocalDate)decisioninputs_dateE);
        }
    }

    public java.time.LocalDate getDecisioninputs_dateD() {
        return this.decisioninputs_dateD;
    }

    public void setDecisioninputs_dateD(java.time.LocalDate decisioninputs_dateD) {
        this.decisioninputs_dateD = decisioninputs_dateD;
    }

    public java.time.LocalDate getDecisioninputs_dateE() {
        return this.decisioninputs_dateE;
    }

    public void setDecisioninputs_dateE(java.time.LocalDate decisioninputs_dateE) {
        this.decisioninputs_dateE = decisioninputs_dateE;
    }

}
