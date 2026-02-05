
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionService_009"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionService_009Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String decision_009_3;

    public DecisionService_009Input_() {
    }

    public DecisionService_009Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decision_009_3 = input_.get("decision_009_3");
            setDecision_009_3((String)decision_009_3);
        }
    }

    public String getDecision_009_3() {
        return this.decision_009_3;
    }

    public void setDecision_009_3(String decision_009_3) {
        this.decision_009_3 = decision_009_3;
    }

}
