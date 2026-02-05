
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "append"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AppendInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String rgb1;
    private String rgb2;

    public AppendInput_() {
    }

    public AppendInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object rgb1 = input_.get("rgb1");
            setRgb1((String)rgb1);
            Object rgb2 = input_.get("rgb2");
            setRgb2((String)rgb2);
        }
    }

    public String getRgb1() {
        return this.rgb1;
    }

    public void setRgb1(String rgb1) {
        this.rgb1 = rgb1;
    }

    public String getRgb2() {
        return this.rgb2;
    }

    public void setRgb2(String rgb2) {
        this.rgb2 = rgb2;
    }

}
