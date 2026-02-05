
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionService_014"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionService_014Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_014_1;
    private String decision_014_3;

    public DecisionService_014Input_() {
    }

    public DecisionService_014Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_014_1 = input_.get("inputData_014_1");
            setInputData_014_1((String)inputData_014_1);
            Object decision_014_3 = input_.get("decision_014_3");
            setDecision_014_3((String)decision_014_3);
        }
    }

    public String getInputData_014_1() {
        return this.inputData_014_1;
    }

    public void setInputData_014_1(String inputData_014_1) {
        this.inputData_014_1 = inputData_014_1;
    }

    public String getDecision_014_3() {
        return this.decision_014_3;
    }

    public void setDecision_014_3(String decision_014_3) {
        this.decision_014_3 = decision_014_3;
    }

}
