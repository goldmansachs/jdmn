
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<Boolean> booleanInput;
    private List<java.time.temporal.TemporalAccessor> dateAndTimeInput;
    private List<java.time.LocalDate> dateInput;
    private List<String> enumerationInput;
    private List<java.lang.Number> numberInput;
    private List<String> textInput;
    private List<java.time.temporal.TemporalAccessor> timeInput;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanInput = input_.get("BooleanInput");
            setBooleanInput((List<Boolean>)booleanInput);
            Object dateAndTimeInput = input_.get("DateAndTimeInput");
            setDateAndTimeInput((List<java.time.temporal.TemporalAccessor>)dateAndTimeInput);
            Object dateInput = input_.get("DateInput");
            setDateInput((List<java.time.LocalDate>)dateInput);
            Object enumerationInput = input_.get("EnumerationInput");
            setEnumerationInput((List<String>)enumerationInput);
            Object numberInput = input_.get("NumberInput");
            setNumberInput((List<java.lang.Number>)numberInput);
            Object textInput = input_.get("TextInput");
            setTextInput((List<String>)textInput);
            Object timeInput = input_.get("TimeInput");
            setTimeInput((List<java.time.temporal.TemporalAccessor>)timeInput);
        }
    }

    public List<Boolean> getBooleanInput() {
        return this.booleanInput;
    }

    public void setBooleanInput(List<Boolean> booleanInput) {
        this.booleanInput = booleanInput;
    }

    public List<java.time.temporal.TemporalAccessor> getDateAndTimeInput() {
        return this.dateAndTimeInput;
    }

    public void setDateAndTimeInput(List<java.time.temporal.TemporalAccessor> dateAndTimeInput) {
        this.dateAndTimeInput = dateAndTimeInput;
    }

    public List<java.time.LocalDate> getDateInput() {
        return this.dateInput;
    }

    public void setDateInput(List<java.time.LocalDate> dateInput) {
        this.dateInput = dateInput;
    }

    public List<String> getEnumerationInput() {
        return this.enumerationInput;
    }

    public void setEnumerationInput(List<String> enumerationInput) {
        this.enumerationInput = enumerationInput;
    }

    public List<java.lang.Number> getNumberInput() {
        return this.numberInput;
    }

    public void setNumberInput(List<java.lang.Number> numberInput) {
        this.numberInput = numberInput;
    }

    public List<String> getTextInput() {
        return this.textInput;
    }

    public void setTextInput(List<String> textInput) {
        this.textInput = textInput;
    }

    public List<java.time.temporal.TemporalAccessor> getTimeInput() {
        return this.timeInput;
    }

    public void setTimeInput(List<java.time.temporal.TemporalAccessor> timeInput) {
        this.timeInput = timeInput;
    }

}
