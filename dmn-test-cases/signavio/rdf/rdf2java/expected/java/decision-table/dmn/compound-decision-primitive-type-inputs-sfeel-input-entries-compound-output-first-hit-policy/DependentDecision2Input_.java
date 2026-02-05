
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number dD2NumberInput;

    public DependentDecision2Input_() {
    }

    public DependentDecision2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dD2NumberInput = input_.get("DD2 Number Input");
            setDD2NumberInput((java.lang.Number)dD2NumberInput);
        }
    }

    public java.lang.Number getDD2NumberInput() {
        return this.dD2NumberInput;
    }

    public void setDD2NumberInput(java.lang.Number dD2NumberInput) {
        this.dD2NumberInput = dD2NumberInput;
    }

}
