
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "mid"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MidInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> inputA;
    private List<java.lang.Number> inputB;

    public MidInput_() {
    }

    public MidInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputA = input_.get("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#inputA");
            setInputA((List<String>)inputA);
            Object inputB = input_.get("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#inputB");
            setInputB((List<java.lang.Number>)inputB);
        }
    }

    public List<String> getInputA() {
        return this.inputA;
    }

    public void setInputA(List<String> inputA) {
        this.inputA = inputA;
    }

    public List<java.lang.Number> getInputB() {
        return this.inputB;
    }

    public void setInputB(List<java.lang.Number> inputB) {
        this.inputB = inputB;
    }

}
