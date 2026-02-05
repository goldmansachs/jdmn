
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanInput;
    private java.time.LocalDate dateInput;
    private String enumerationInput;
    private java.lang.Number numberInput;
    private type.Person person;
    private String stringInput;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanInput = input_.get("BooleanInput");
            setBooleanInput((Boolean)booleanInput);
            Object dateInput = input_.get("DateInput");
            setDateInput((java.time.LocalDate)dateInput);
            Object enumerationInput = input_.get("EnumerationInput");
            setEnumerationInput((String)enumerationInput);
            Object numberInput = input_.get("NumberInput");
            setNumberInput((java.lang.Number)numberInput);
            Object person = input_.get("Person");
            setPerson(type.Person.toPerson(null));
            Object stringInput = input_.get("StringInput");
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

    public type.Person getPerson() {
        return this.person;
    }

    public void setPerson(type.Person person) {
        this.person = person;
    }

    public String getStringInput() {
        return this.stringInput;
    }

    public void setStringInput(String stringInput) {
        this.stringInput = stringInput;
    }

}
