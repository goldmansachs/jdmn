
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TestPersonType testPersonType6_iterator;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
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
