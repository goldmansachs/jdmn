
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanInput;
    private java.time.LocalDate dateInput;
    private String enumerationInput;
    private java.lang.Number numberInput;
    private String stringInput;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanInput = input_.get("http://www.provider.com/dmn/1.1/diagram/74965ae3e6454dd6b9d3c79b856d84ba.xml#booleanInput");
            setBooleanInput((Boolean)booleanInput);
            Object dateInput = input_.get("http://www.provider.com/dmn/1.1/diagram/74965ae3e6454dd6b9d3c79b856d84ba.xml#dateInput");
            setDateInput((java.time.LocalDate)dateInput);
            Object enumerationInput = input_.get("http://www.provider.com/dmn/1.1/diagram/74965ae3e6454dd6b9d3c79b856d84ba.xml#enumerationInput");
            setEnumerationInput((String)enumerationInput);
            Object numberInput = input_.get("http://www.provider.com/dmn/1.1/diagram/74965ae3e6454dd6b9d3c79b856d84ba.xml#numberInput");
            setNumberInput((java.lang.Number)numberInput);
            Object stringInput = input_.get("http://www.provider.com/dmn/1.1/diagram/74965ae3e6454dd6b9d3c79b856d84ba.xml#stringInput");
            setStringInput((String)stringInput);
        }
    }

    public Boolean getBooleanInput() {
        return this.booleanInput;
    }

    public void setBooleanInput(Boolean booleanInput) {
        this.booleanInput = booleanInput;
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

    public String getStringInput() {
        return this.stringInput;
    }

    public void setStringInput(String stringInput) {
        this.stringInput = stringInput;
    }

}
