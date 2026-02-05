
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionService_007"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionService_007Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String decision_007_3;

    public DecisionService_007Input_() {
    }

    public DecisionService_007Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decision_007_3 = input_.get("decision_007_3");
            setDecision_007_3((String)decision_007_3);
        }
    }

    public String getDecision_007_3() {
        return this.decision_007_3;
    }

    public void setDecision_007_3(String decision_007_3) {
        this.decision_007_3 = decision_007_3;
    }

}
