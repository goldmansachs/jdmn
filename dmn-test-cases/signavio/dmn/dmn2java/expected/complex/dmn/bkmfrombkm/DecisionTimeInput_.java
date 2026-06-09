
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionTimeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor timeInput;

    public DecisionTimeInput_() {
    }

    public DecisionTimeInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object timeInput = input_.get("http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml#timeInput");
            setTimeInput((java.time.temporal.TemporalAccessor)timeInput);
        }
    }

    public java.time.temporal.TemporalAccessor getTimeInput() {
        return this.timeInput;
    }

    public void setTimeInput(java.time.temporal.TemporalAccessor timeInput) {
        this.timeInput = timeInput;
    }

}
