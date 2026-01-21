
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.time.LocalDate d;
        private java.time.temporal.TemporalAccessor t;

    public java.time.LocalDate getD() {
        return this.d;
    }

    public void setD(java.time.LocalDate d) {
        this.d = d;
    }

    public java.time.temporal.TemporalAccessor getT() {
        return this.t;
    }

    public void setT(java.time.temporal.TemporalAccessor t) {
        this.t = t;
    }

}
