
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_017_1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_017_1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String input_017_1;

    public Decision_017_1Input_() {
    }

    public Decision_017_1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object input_017_1 = input_.get("input_017_1");
            setInput_017_1((String)input_017_1);
        }
    }

    public String getInput_017_1() {
        return this.input_017_1;
    }

    public void setInput_017_1(String input_017_1) {
        this.input_017_1 = input_017_1;
    }

}
