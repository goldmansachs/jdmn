
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bKM"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BKMInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number a;
    private java.lang.Number b;

    public BKMInput_() {
    }

    public BKMInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a = input_.get("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#a");
            setA((java.lang.Number)a);
            Object b = input_.get("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#b");
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
