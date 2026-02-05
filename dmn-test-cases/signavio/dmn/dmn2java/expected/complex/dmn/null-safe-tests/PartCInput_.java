
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "partC"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PartCInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanA;
    private Boolean booleanB;
    private java.time.LocalDate date;
    private java.time.temporal.TemporalAccessor dateTime;
    private java.time.temporal.TemporalAccessor time;

    public PartCInput_() {
    }

    public PartCInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanA = input_.get("booleanA");
            setBooleanA((Boolean)booleanA);
            Object booleanB = input_.get("booleanB");
            setBooleanB((Boolean)booleanB);
            Object date = input_.get("date");
            setDate((java.time.LocalDate)date);
            Object dateTime = input_.get("dateTime");
            setDateTime((java.time.temporal.TemporalAccessor)dateTime);
            Object time = input_.get("time");
            setTime((java.time.temporal.TemporalAccessor)time);
        }
    }

    public Boolean getBooleanA() {
        return this.booleanA;
    }

    public void setBooleanA(Boolean booleanA) {
        this.booleanA = booleanA;
    }

    public Boolean getBooleanB() {
        return this.booleanB;
    }

    public void setBooleanB(Boolean booleanB) {
        this.booleanB = booleanB;
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
