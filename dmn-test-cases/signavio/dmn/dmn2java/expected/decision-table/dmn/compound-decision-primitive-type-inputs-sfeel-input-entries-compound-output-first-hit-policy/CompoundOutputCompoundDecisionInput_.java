
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compoundOutputCompoundDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompoundOutputCompoundDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private Boolean booleanInput;
        private String dd1TextInput;
        private java.lang.Number dd2NumberInput;
        private String enumerationInput;

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
