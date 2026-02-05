
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "FACT"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FACTInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number n;

    public FACTInput_() {
    }

    public FACTInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object n = input_.get("n");
            setN((java.lang.Number)n);
        }
    }

    public java.lang.Number getN() {
        return this.n;
    }

    public void setN(java.lang.Number n) {
        this.n = n;
    }

}
