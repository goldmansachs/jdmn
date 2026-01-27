
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "timeOperators"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TimeOperatorsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor time;

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

}
