
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "testNullComplexTypeAccess"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TestNullComplexTypeAccessInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String inputString;

    public TestNullComplexTypeAccessInput_() {
    }

    public TestNullComplexTypeAccessInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputString = input_.get("http://www.provider.com/dmn/1.1/diagram/7f0dd69d49504172be8e6e3c23d8ed63.xml#inputString");
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
