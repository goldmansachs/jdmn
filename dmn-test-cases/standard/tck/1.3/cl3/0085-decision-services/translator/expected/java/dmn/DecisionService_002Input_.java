
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionService_002"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionService_002Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String decision_002_input;

    public DecisionService_002Input_() {
    }

    public DecisionService_002Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decision_002_input = input_.get("decision_002_input");
            setDecision_002_input((String)decision_002_input);
        }
    }

    public String getDecision_002_input() {
        return this.decision_002_input;
    }

    public void setDecision_002_input(String decision_002_input) {
        this.decision_002_input = decision_002_input;
    }

}
