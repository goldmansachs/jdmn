
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dependentDecision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number dd2NumberInput;

    public DependentDecision2Input_() {
    }

    public DependentDecision2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dd2NumberInput = input_.get("http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml#dd2NumberInput");
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
