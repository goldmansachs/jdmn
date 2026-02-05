
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "timeOperators"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TimeOperatorsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor time;

    public TimeOperatorsInput_() {
    }

    public TimeOperatorsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object time = input_.get("time");
            setTime((java.time.temporal.TemporalAccessor)time);
        }
    }

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

}
