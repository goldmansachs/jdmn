
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> ages;
    private java.lang.Number day;
    private java.lang.Number hour;
    private java.lang.Number minute;
    private java.lang.Number month;
    private List<String> names;
    private java.lang.Number second;
    private java.lang.Number year;

    public ZipInput_() {
    }

    public ZipInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object ages = input_.get("ages");
            setAges((List<java.lang.Number>)ages);
            Object day = input_.get("day");
            setDay((java.lang.Number)day);
            Object hour = input_.get("hour");
            setHour((java.lang.Number)hour);
            Object minute = input_.get("minute");
            setMinute((java.lang.Number)minute);
            Object month = input_.get("month");
            setMonth((java.lang.Number)month);
            Object names = input_.get("names");
            setNames((List<String>)names);
            Object second = input_.get("second");
            setSecond((java.lang.Number)second);
            Object year = input_.get("year");
            setYear((java.lang.Number)year);
        }
    }

    public List<java.lang.Number> getAges() {
        return this.ages;
    }

    public void setAges(List<java.lang.Number> ages) {
        this.ages = ages;
    }

    public java.lang.Number getDay() {
        return this.day;
    }

    public void setDay(java.lang.Number day) {
        this.day = day;
    }

    public java.lang.Number getHour() {
        return this.hour;
    }

    public void setHour(java.lang.Number hour) {
        this.hour = hour;
    }

    public java.lang.Number getMinute() {
        return this.minute;
    }

    public void setMinute(java.lang.Number minute) {
        this.minute = minute;
    }

    public java.lang.Number getMonth() {
        return this.month;
    }

    public void setMonth(java.lang.Number month) {
        this.month = month;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public java.lang.Number getSecond() {
        return this.second;
    }

    public void setSecond(java.lang.Number second) {
        this.second = second;
    }

    public java.lang.Number getYear() {
        return this.year;
    }

    public void setYear(java.lang.Number year) {
        this.year = year;
    }

}
