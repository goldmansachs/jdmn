
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "FUNCT"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FUNCTInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> numberList;
    private java.lang.Number number;
    private java.time.temporal.TemporalAccessor dateTime;

    public FUNCTInput_() {
    }

    public FUNCTInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberList = input_.get("numberList");
            setNumberList((List<java.lang.Number>)numberList);
            Object number = input_.get("number");
            setNumber((java.lang.Number)number);
            Object dateTime = input_.get("dateTime");
            setDateTime((java.time.temporal.TemporalAccessor)dateTime);
        }
    }

    public List<java.lang.Number> getNumberList() {
        return this.numberList;
    }

    public void setNumberList(List<java.lang.Number> numberList) {
        this.numberList = numberList;
    }

    public java.lang.Number getNumber() {
        return this.number;
    }

    public void setNumber(java.lang.Number number) {
        this.number = number;
    }

    public java.time.temporal.TemporalAccessor getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(java.time.temporal.TemporalAccessor dateTime) {
        this.dateTime = dateTime;
    }

}
