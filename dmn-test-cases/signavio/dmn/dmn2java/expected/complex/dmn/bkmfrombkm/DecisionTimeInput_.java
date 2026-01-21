
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionTimeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.time.temporal.TemporalAccessor timeInput;

    public java.time.temporal.TemporalAccessor getTimeInput() {
        return this.timeInput;
    }

    public void setTimeInput(java.time.temporal.TemporalAccessor timeInput) {
        this.timeInput = timeInput;
    }

}
