
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compoundOutputDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompoundOutputDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanInput;
    private java.time.temporal.TemporalAccessor dateAndTimeInput;
    private java.time.LocalDate dateInput;
    private String enumerationInput;
    private java.lang.Number numberInput;
    private String textInput;
    private java.time.temporal.TemporalAccessor timeInput;

    public CompoundOutputDecisionInput_() {
    }

    public CompoundOutputDecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanInput = input_.get("BooleanInput");
            setBooleanInput((Boolean)booleanInput);
            Object dateAndTimeInput = input_.get("DateAndTimeInput");
            setDateAndTimeInput((java.time.temporal.TemporalAccessor)dateAndTimeInput);
            Object dateInput = input_.get("DateInput");
            setDateInput((java.time.LocalDate)dateInput);
            Object enumerationInput = input_.get("EnumerationInput");
            setEnumerationInput((String)enumerationInput);
            Object numberInput = input_.get("NumberInput");
            setNumberInput((java.lang.Number)numberInput);
            Object textInput = input_.get("TextInput");
            setTextInput((String)textInput);
            Object timeInput = input_.get("TimeInput");
            setTimeInput((java.time.temporal.TemporalAccessor)timeInput);
        }
    }

    public Boolean getBooleanInput() {
        return this.booleanInput;
    }

    public void setBooleanInput(Boolean booleanInput) {
        this.booleanInput = booleanInput;
    }

    public java.time.temporal.TemporalAccessor getDateAndTimeInput() {
        return this.dateAndTimeInput;
    }

    public void setDateAndTimeInput(java.time.temporal.TemporalAccessor dateAndTimeInput) {
        this.dateAndTimeInput = dateAndTimeInput;
    }

    public java.time.LocalDate getDateInput() {
        return this.dateInput;
    }

    public void setDateInput(java.time.LocalDate dateInput) {
        this.dateInput = dateInput;
    }

    public String getEnumerationInput() {
        return this.enumerationInput;
    }

    public void setEnumerationInput(String enumerationInput) {
        this.enumerationInput = enumerationInput;
    }

    public java.lang.Number getNumberInput() {
        return this.numberInput;
    }

    public void setNumberInput(java.lang.Number numberInput) {
        this.numberInput = numberInput;
    }

    public String getTextInput() {
        return this.textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public java.time.temporal.TemporalAccessor getTimeInput() {
        return this.timeInput;
    }

    public void setTimeInput(java.time.temporal.TemporalAccessor timeInput) {
        this.timeInput = timeInput;
    }

}
