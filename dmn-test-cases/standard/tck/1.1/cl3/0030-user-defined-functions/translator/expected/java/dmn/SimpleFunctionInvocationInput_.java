
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "simple function invocation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class SimpleFunctionInvocationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String stringInputA;
    private String stringInputB;

    public SimpleFunctionInvocationInput_() {
    }

    public SimpleFunctionInvocationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object stringInputA = input_.get("stringInputA");
            setStringInputA((String)stringInputA);
            Object stringInputB = input_.get("stringInputB");
            setStringInputB((String)stringInputB);
        }
    }

    public String getStringInputA() {
        return this.stringInputA;
    }

    public void setStringInputA(String stringInputA) {
        this.stringInputA = stringInputA;
    }

    public String getStringInputB() {
        return this.stringInputB;
    }

    public void setStringInputB(String stringInputB) {
        this.stringInputB = stringInputB;
    }

}
