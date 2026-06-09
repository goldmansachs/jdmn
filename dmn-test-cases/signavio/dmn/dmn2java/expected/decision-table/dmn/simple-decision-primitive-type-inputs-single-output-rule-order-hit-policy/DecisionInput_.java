
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberInput;
    private String textInput;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberInput = input_.get("http://www.provider.com/dmn/1.1/diagram/5ff0a678d67f4e05b040ba5f2114ffd6.xml#numberInput");
            setNumberInput((java.lang.Number)numberInput);
            Object textInput = input_.get("http://www.provider.com/dmn/1.1/diagram/5ff0a678d67f4e05b040ba5f2114ffd6.xml#textInput");
            setTextInput((String)textInput);
        }
    }

    public java.lang.Number getNumberInput() {
        return this.numberInput;
    }

    public void setNumberInput(java.lang.Number numberInput) {
        this.numberInput = numberInput;
    }

    public String getTextInput() {
        return this.textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

}
