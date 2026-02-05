
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "zip1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Zip1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> a4;
    private List<java.lang.Number> b;

    public Zip1Input_() {
    }

    public Zip1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a4 = input_.get("A");
            setA4((List<java.lang.Number>)a4);
            Object b = input_.get("B");
            setB((List<java.lang.Number>)b);
        }
    }

    public List<java.lang.Number> getA4() {
        return this.a4;
    }

    public void setA4(List<java.lang.Number> a4) {
        this.a4 = a4;
    }

    public List<java.lang.Number> getB() {
        return this.b;
    }

    public void setB(List<java.lang.Number> b) {
        this.b = b;
    }

}
