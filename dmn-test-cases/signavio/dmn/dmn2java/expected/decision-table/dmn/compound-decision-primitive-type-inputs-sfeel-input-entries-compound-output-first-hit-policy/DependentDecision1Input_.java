
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String dd1TextInput;

    public DependentDecision1Input_() {
    }

    public DependentDecision1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dd1TextInput = input_.get("http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml#dd1TextInput");
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
