
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_013_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_013_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number input_013_1;

    public Decision_013_2Input_() {
    }

    public Decision_013_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object input_013_1 = input_.get("input_013_1");
            setInput_013_1((java.lang.Number)input_013_1);
        }
    }

    public java.lang.Number getInput_013_1() {
        return this.input_013_1;
    }

    public void setInput_013_1(java.lang.Number input_013_1) {
        this.input_013_1 = input_013_1;
    }

}
