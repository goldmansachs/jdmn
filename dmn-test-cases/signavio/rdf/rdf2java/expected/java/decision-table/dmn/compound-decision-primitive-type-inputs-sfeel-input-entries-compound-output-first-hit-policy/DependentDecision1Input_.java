
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String dD1TextInput;

    public DependentDecision1Input_() {
    }

    public DependentDecision1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dD1TextInput = input_.get("DD1 Text Input");
            setDD1TextInput((String)dD1TextInput);
        }
    }

    public String getDD1TextInput() {
        return this.dD1TextInput;
    }

    public void setDD1TextInput(String dD1TextInput) {
        this.dD1TextInput = dD1TextInput;
    }

}
