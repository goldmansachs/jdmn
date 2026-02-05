
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "testNullComplexTypeAccess"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TestNullComplexTypeAccessInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputString;

    public TestNullComplexTypeAccessInput_() {
    }

    public TestNullComplexTypeAccessInput_(com.gs.dmn.runtime.Context input_) {
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
