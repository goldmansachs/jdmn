
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compoundOutputCompoundDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompoundOutputCompoundDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanInput;
    private String dd1TextInput;
    private java.lang.Number dd2NumberInput;
    private String enumerationInput;

    public CompoundOutputCompoundDecisionInput_() {
    }

    public CompoundOutputCompoundDecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanInput = input_.get("BooleanInput");
            setBooleanInput((Boolean)booleanInput);
            Object dd1TextInput = input_.get("DD1 Text Input");
            setDd1TextInput((String)dd1TextInput);
            Object dd2NumberInput = input_.get("DD2 Number Input");
            setDd2NumberInput((java.lang.Number)dd2NumberInput);
            Object enumerationInput = input_.get("EnumerationInput");
            setEnumerationInput((String)enumerationInput);
        }
    }

    public Boolean getBooleanInput() {
        return this.booleanInput;
    }

    public void setBooleanInput(Boolean booleanInput) {
        this.booleanInput = booleanInput;
    }

    public String getDd1TextInput() {
        return this.dd1TextInput;
    }

    public void setDd1TextInput(String dd1TextInput) {
        this.dd1TextInput = dd1TextInput;
    }

    public java.lang.Number getDd2NumberInput() {
        return this.dd2NumberInput;
    }

    public void setDd2NumberInput(java.lang.Number dd2NumberInput) {
        this.dd2NumberInput = dd2NumberInput;
    }

    public String getEnumerationInput() {
        return this.enumerationInput;
    }

    public void setEnumerationInput(String enumerationInput) {
        this.enumerationInput = enumerationInput;
    }

}
