
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_003"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_003Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_003;

    public Decision_003Input_() {
    }

    public Decision_003Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_003 = input_.get("inputData_003");
            setInputData_003((String)inputData_003);
        }
    }

    public String getInputData_003() {
        return this.inputData_003;
    }

    public void setInputData_003(String inputData_003) {
        this.inputData_003 = inputData_003;
    }

}
