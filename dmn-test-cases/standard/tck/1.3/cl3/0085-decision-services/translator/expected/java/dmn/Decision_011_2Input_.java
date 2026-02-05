
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision_011_2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision_011_2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_011_1;
    private String inputData_011_2;

    public Decision_011_2Input_() {
    }

    public Decision_011_2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_011_1 = input_.get("inputData_011_1");
            setInputData_011_1((String)inputData_011_1);
            Object inputData_011_2 = input_.get("inputData_011_2");
            setInputData_011_2((String)inputData_011_2);
        }
    }

    public String getInputData_011_1() {
        return this.inputData_011_1;
    }

    public void setInputData_011_1(String inputData_011_1) {
        this.inputData_011_1 = inputData_011_1;
    }

    public String getInputData_011_2() {
        return this.inputData_011_2;
    }

    public void setInputData_011_2(String inputData_011_2) {
        this.inputData_011_2 = inputData_011_2;
    }

}
