
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionService_003"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionService_003Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputData_003;
    private String decision_003_input_1;
    private String decision_003_input_2;

    public DecisionService_003Input_() {
    }

    public DecisionService_003Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputData_003 = input_.get("inputData_003");
            setInputData_003((String)inputData_003);
            Object decision_003_input_1 = input_.get("decision_003_input_1");
            setDecision_003_input_1((String)decision_003_input_1);
            Object decision_003_input_2 = input_.get("decision_003_input_2");
            setDecision_003_input_2((String)decision_003_input_2);
        }
    }

    public String getInputData_003() {
        return this.inputData_003;
    }

    public void setInputData_003(String inputData_003) {
        this.inputData_003 = inputData_003;
    }

    public String getDecision_003_input_1() {
        return this.decision_003_input_1;
    }

    public void setDecision_003_input_1(String decision_003_input_1) {
        this.decision_003_input_1 = decision_003_input_1;
    }

    public String getDecision_003_input_2() {
        return this.decision_003_input_2;
    }

    public void setDecision_003_input_2(String decision_003_input_2) {
        this.decision_003_input_2 = decision_003_input_2;
    }

}
