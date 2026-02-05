
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "main"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MainInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number n;

    public MainInput_() {
    }

    public MainInput_(com.gs.dmn.runtime.Context input_) {
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
