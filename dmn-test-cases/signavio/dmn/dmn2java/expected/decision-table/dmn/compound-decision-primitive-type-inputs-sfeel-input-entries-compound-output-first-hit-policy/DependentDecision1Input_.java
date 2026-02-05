
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String dd1TextInput;

    public DependentDecision1Input_() {
    }

    public DependentDecision1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dd1TextInput = input_.get("DD1 Text Input");
            setDd1TextInput((String)dd1TextInput);
        }
    }

    public String getDd1TextInput() {
        return this.dd1TextInput;
    }

    public void setDd1TextInput(String dd1TextInput) {
        this.dd1TextInput = dd1TextInput;
    }

}
