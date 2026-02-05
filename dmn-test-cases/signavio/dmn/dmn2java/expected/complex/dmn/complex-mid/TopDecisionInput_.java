
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "topDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TopDecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TestPersonType testPersonType6_iterator;

    public TopDecisionInput_() {
    }

    public TopDecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object testPersonType6_iterator = input_.get("TestPersonType");
            setTestPersonType6_iterator(type.TestPersonType.toTestPersonType(null));
        }
    }

    public type.TestPersonType getTestPersonType6_iterator() {
        return this.testPersonType6_iterator;
    }

    public void setTestPersonType6_iterator(type.TestPersonType testPersonType6_iterator) {
        this.testPersonType6_iterator = testPersonType6_iterator;
    }

}
