
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "modelCDecisionBasedOnBs"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ModelCDecisionBasedOnBsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String personName;

    public ModelCDecisionBasedOnBsInput_() {
    }

    public ModelCDecisionBasedOnBsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object personName = input_.get("Person Name");
            setPersonName((String)personName);
        }
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}
