
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_012_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_012_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_012_1;
    private String inputData_012_2;

    public Decision_012_2Input_() {
    }

    public Decision_012_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_012_1 = input_.get("inputData_012_1");
            setInputData_012_1((String)inputData_012_1);
            Object inputData_012_2 = input_.get("inputData_012_2");
            setInputData_012_2((String)inputData_012_2);
        }
    }

    public String getInputData_012_1() {
        return this.inputData_012_1;
    }

    public void setInputData_012_1(String inputData_012_1) {
        this.inputData_012_1 = inputData_012_1;
    }

    public String getInputData_012_2() {
        return this.inputData_012_2;
    }

    public void setInputData_012_2(String inputData_012_2) {
        this.inputData_012_2 = inputData_012_2;
    }

}
