
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "abc"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AbcInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number num;

    public AbcInput_() {
    }

    public AbcInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object num = input_.get("num");
            setNum((java.lang.Number)num);
        }
    }

    public java.lang.Number getNum() {
        return this.num;
    }

    public void setNum(java.lang.Number num) {
        this.num = num;
    }

}
