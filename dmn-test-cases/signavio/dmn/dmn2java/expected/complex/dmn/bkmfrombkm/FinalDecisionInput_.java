
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "finalDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FinalDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate dateInput;
    private java.time.temporal.TemporalAccessor timeInput;

    public FinalDecisionInput_() {
    }

    public FinalDecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateInput = input_.get("http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml#dateInput");
            setDateInput((java.time.LocalDate)dateInput);
            Object timeInput = input_.get("http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml#timeInput");
            setTimeInput((java.time.temporal.TemporalAccessor)timeInput);
        }
    }

    public java.time.LocalDate getDateInput() {
        return this.dateInput;
    }

    public void setDateInput(java.time.LocalDate dateInput) {
        this.dateInput = dateInput;
    }

    public java.time.temporal.TemporalAccessor getTimeInput() {
        return this.timeInput;
    }

    public void setTimeInput(java.time.temporal.TemporalAccessor timeInput) {
        this.timeInput = timeInput;
    }

}
