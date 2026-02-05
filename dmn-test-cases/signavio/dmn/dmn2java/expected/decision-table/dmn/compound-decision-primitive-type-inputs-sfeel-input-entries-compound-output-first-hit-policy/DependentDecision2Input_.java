
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number dd2NumberInput;

    public DependentDecision2Input_() {
    }

    public DependentDecision2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dd2NumberInput = input_.get("DD2 Number Input");
            setDd2NumberInput((java.lang.Number)dd2NumberInput);
        }
    }

    public java.lang.Number getDd2NumberInput() {
        return this.dd2NumberInput;
    }

    public void setDd2NumberInput(java.lang.Number dd2NumberInput) {
        this.dd2NumberInput = dd2NumberInput;
    }

}
