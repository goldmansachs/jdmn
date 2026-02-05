
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "buildDateStringInAnnotation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BuildDateStringInAnnotationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number day;
    private java.lang.Number month;
    private java.lang.Number year;

    public BuildDateStringInAnnotationInput_() {
    }

    public BuildDateStringInAnnotationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object day = input_.get("day");
            setDay((java.lang.Number)day);
            Object month = input_.get("month");
            setMonth((java.lang.Number)month);
            Object year = input_.get("year");
            setYear((java.lang.Number)year);
        }
    }

    public java.lang.Number getDay() {
        return this.day;
    }

    public void setDay(java.lang.Number day) {
        this.day = day;
    }

    public java.lang.Number getMonth() {
        return this.month;
    }

    public void setMonth(java.lang.Number month) {
        this.month = month;
    }

    public java.lang.Number getYear() {
        return this.year;
    }

    public void setYear(java.lang.Number year) {
        this.year = year;
    }

}
