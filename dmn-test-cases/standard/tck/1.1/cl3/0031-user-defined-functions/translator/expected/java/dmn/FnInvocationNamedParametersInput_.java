
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "fn invocation named parameters"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FnInvocationNamedParametersInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number inputA;
    private java.lang.Number inputB;

    public FnInvocationNamedParametersInput_() {
    }

    public FnInvocationNamedParametersInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputA = input_.get("inputA");
            setInputA((java.lang.Number)inputA);
            Object inputB = input_.get("inputB");
            setInputB((java.lang.Number)inputB);
        }
    }

    public java.lang.Number getInputA() {
        return this.inputA;
    }

    public void setInputA(java.lang.Number inputA) {
        this.inputA = inputA;
    }

    public java.lang.Number getInputB() {
        return this.inputB;
    }

    public void setInputB(java.lang.Number inputB) {
        this.inputB = inputB;
    }

}
