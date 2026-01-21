
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compoundOutputCompoundDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompoundOutputCompoundDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private Boolean booleanInput;
        private String dD1TextInput;
        private java.lang.Number dD2NumberInput;
        private String enumerationInput;

    public Boolean getBooleanInput() {
        return this.booleanInput;
    }

    public void setBooleanInput(Boolean booleanInput) {
        this.booleanInput = booleanInput;
    }

    public String getDD1TextInput() {
        return this.dD1TextInput;
    }

    public void setDD1TextInput(String dD1TextInput) {
        this.dD1TextInput = dD1TextInput;
    }

    public java.lang.Number getDD2NumberInput() {
        return this.dD2NumberInput;
    }

    public void setDD2NumberInput(java.lang.Number dD2NumberInput) {
        this.dD2NumberInput = dD2NumberInput;
    }

    public String getEnumerationInput() {
        return this.enumerationInput;
    }

    public void setEnumerationInput(String enumerationInput) {
        this.enumerationInput = enumerationInput;
    }

}
