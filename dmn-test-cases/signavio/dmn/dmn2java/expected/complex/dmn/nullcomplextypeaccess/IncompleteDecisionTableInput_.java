
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "incompleteDecisionTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class IncompleteDecisionTableInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputString;

    public IncompleteDecisionTableInput_() {
    }

    public IncompleteDecisionTableInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputString = input_.get("InputString");
            setInputString((String)inputString);
        }
    }

    public String getInputString() {
        return this.inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

}
