
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_007_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_007_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number input_007_1;

    public Decision_007_2Input_() {
    }

    public Decision_007_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object input_007_1 = input_.get("input_007_1");
            setInput_007_1((java.lang.Number)input_007_1);
        }
    }

    public java.lang.Number getInput_007_1() {
        return this.input_007_1;
    }

    public void setInput_007_1(java.lang.Number input_007_1) {
        this.input_007_1 = input_007_1;
    }

}
