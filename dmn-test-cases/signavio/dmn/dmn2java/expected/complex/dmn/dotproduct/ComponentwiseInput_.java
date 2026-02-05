
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "componentwise"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ComponentwiseInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> a;
    private List<java.lang.Number> b;

    public ComponentwiseInput_() {
    }

    public ComponentwiseInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a = input_.get("A");
            setA((List<java.lang.Number>)a);
            Object b = input_.get("B");
            setB((List<java.lang.Number>)b);
        }
    }

    public List<java.lang.Number> getA() {
        return this.a;
    }

    public void setA(List<java.lang.Number> a) {
        this.a = a;
    }

    public List<java.lang.Number> getB() {
        return this.b;
    }

    public void setB(List<java.lang.Number> b) {
        this.b = b;
    }

}
