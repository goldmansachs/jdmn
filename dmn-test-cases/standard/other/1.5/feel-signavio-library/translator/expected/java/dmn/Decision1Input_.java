
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Decision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputString;

    public Decision1Input_() {
    }

    public Decision1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputString = input_.get("inputString");
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
