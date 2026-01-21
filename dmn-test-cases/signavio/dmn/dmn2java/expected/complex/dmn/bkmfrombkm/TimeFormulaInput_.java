
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "timeFormula"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TimeFormulaInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.time.temporal.TemporalAccessor time;
        private java.time.temporal.TemporalAccessor time2;

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

    public java.time.temporal.TemporalAccessor getTime2() {
        return this.time2;
    }

    public void setTime2(java.time.temporal.TemporalAccessor time2) {
        this.time2 = time2;
    }

}
