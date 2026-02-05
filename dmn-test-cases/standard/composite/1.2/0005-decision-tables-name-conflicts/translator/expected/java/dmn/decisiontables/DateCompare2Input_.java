package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate decisioninputs1_dateD;
    private java.time.LocalDate decisioninputs2_dateD;

    public DateCompare2Input_() {
    }

    public DateCompare2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs1_dateD = input_.get("decisionInputs1.dateD");
            setDecisioninputs1_dateD((java.time.LocalDate)decisioninputs1_dateD);
            Object decisioninputs2_dateD = input_.get("decisionInputs2.dateD");
            setDecisioninputs2_dateD((java.time.LocalDate)decisioninputs2_dateD);
        }
    }

    public java.time.LocalDate getDecisioninputs1_dateD() {
        return this.decisioninputs1_dateD;
    }

    public void setDecisioninputs1_dateD(java.time.LocalDate decisioninputs1_dateD) {
        this.decisioninputs1_dateD = decisioninputs1_dateD;
    }

    public java.time.LocalDate getDecisioninputs2_dateD() {
        return this.decisioninputs2_dateD;
    }

    public void setDecisioninputs2_dateD(java.time.LocalDate decisioninputs2_dateD) {
        this.decisioninputs2_dateD = decisioninputs2_dateD;
    }

}
