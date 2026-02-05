
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "temporalDiff"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TemporalDiffInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate date;
    private java.time.temporal.TemporalAccessor dateTime;
    private java.time.temporal.TemporalAccessor time;

    public TemporalDiffInput_() {
    }

    public TemporalDiffInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object date = input_.get("date");
            setDate((java.time.LocalDate)date);
            Object dateTime = input_.get("dateTime");
            setDateTime((java.time.temporal.TemporalAccessor)dateTime);
            Object time = input_.get("time");
            setTime((java.time.temporal.TemporalAccessor)time);
        }
    }

    public java.time.LocalDate getDate() {
        return this.date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public java.time.temporal.TemporalAccessor getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(java.time.temporal.TemporalAccessor dateTime) {
        this.dateTime = dateTime;
    }

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

}
