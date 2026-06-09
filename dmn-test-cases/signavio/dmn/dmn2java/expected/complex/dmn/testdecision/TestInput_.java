
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "test"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TestInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String stringInput;

    public TestInput_() {
    }

    public TestInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object stringInput = input_.get("http://www.provider.com/dmn/1.1/diagram/85f2dd29e4774c2f84b883545afdd8cc.xml#stringInput");
            setStringInput((String)stringInput);
        }
    }

    public String getStringInput() {
        return this.stringInput;
    }

    public void setStringInput(String stringInput) {
        this.stringInput = stringInput;
    }

}
