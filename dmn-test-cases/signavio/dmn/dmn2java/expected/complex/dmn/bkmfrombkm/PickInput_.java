
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "pick"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PickInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.time.LocalDate date;
        private java.time.temporal.TemporalAccessor datetime;
        private java.time.temporal.TemporalAccessor time;

    public java.time.LocalDate getDate() {
        return this.date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public java.time.temporal.TemporalAccessor getDatetime() {
        return this.datetime;
    }

    public void setDatetime(java.time.temporal.TemporalAccessor datetime) {
        this.datetime = datetime;
    }

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

}
