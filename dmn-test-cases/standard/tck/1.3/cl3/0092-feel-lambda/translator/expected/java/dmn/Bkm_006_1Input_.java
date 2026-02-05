
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm_006_1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Bkm_006_1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number a;

    public Bkm_006_1Input_() {
    }

    public Bkm_006_1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object a = input_.get("a");
            setA((java.lang.Number)a);
        }
    }

    public java.lang.Number getA() {
        return this.a;
    }

    public void setA(java.lang.Number a) {
        this.a = a;
    }

}
