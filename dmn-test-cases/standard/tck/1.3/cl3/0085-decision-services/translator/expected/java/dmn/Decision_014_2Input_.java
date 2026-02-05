
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_014_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_014_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_014_1;

    public Decision_014_2Input_() {
    }

    public Decision_014_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_014_1 = input_.get("inputData_014_1");
            setInputData_014_1((String)inputData_014_1);
        }
    }

    public String getInputData_014_1() {
        return this.inputData_014_1;
    }

    public void setInputData_014_1(String inputData_014_1) {
        this.inputData_014_1 = inputData_014_1;
    }

}
