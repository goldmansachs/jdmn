
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number a;
    private java.lang.Number b;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a = input_.get("http://www.provider.com/dmn/1.1/diagram/c5718c9e0d74413e9371e1c26c4ebef9.xml#a");
            setA((java.lang.Number)a);
            Object b = input_.get("http://www.provider.com/dmn/1.1/diagram/c5718c9e0d74413e9371e1c26c4ebef9.xml#b");
            setB((java.lang.Number)b);
        }
    }

    public java.lang.Number getA() {
        return this.a;
    }

    public void setA(java.lang.Number a) {
        this.a = a;
    }

    public java.lang.Number getB() {
        return this.b;
    }

    public void setB(java.lang.Number b) {
        this.b = b;
    }

}
