
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_013_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_013_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_013_1;

    public Decision_013_2Input_() {
    }

    public Decision_013_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_013_1 = input_.get("inputData_013_1");
            setInputData_013_1((String)inputData_013_1);
        }
    }

    public String getInputData_013_1() {
        return this.inputData_013_1;
    }

    public void setInputData_013_1(String inputData_013_1) {
        this.inputData_013_1 = inputData_013_1;
    }

}
