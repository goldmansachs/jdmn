
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "sign"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class SignInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number a2;
    private java.lang.Number b3;

    public SignInput_() {
    }

    public SignInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a2 = input_.get("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#a2");
            setA2((java.lang.Number)a2);
            Object b3 = input_.get("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#b3");
            setB3((java.lang.Number)b3);
        }
    }

    public java.lang.Number getA2() {
        return this.a2;
    }

    public void setA2(java.lang.Number a2) {
        this.a2 = a2;
    }

    public java.lang.Number getB3() {
        return this.b3;
    }

    public void setB3(java.lang.Number b3) {
        this.b3 = b3;
    }

}
