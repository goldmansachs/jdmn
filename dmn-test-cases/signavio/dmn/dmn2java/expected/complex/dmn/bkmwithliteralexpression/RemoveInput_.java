
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "remove"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RemoveInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String rgb2;
    private List<String> rgb2List;

    public RemoveInput_() {
    }

    public RemoveInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object rgb2 = input_.get("rgb2");
            setRgb2((String)rgb2);
            Object rgb2List = input_.get("rgb2 list");
            setRgb2List((List<String>)rgb2List);
        }
    }

    public String getRgb2() {
        return this.rgb2;
    }

    public void setRgb2(String rgb2) {
        this.rgb2 = rgb2;
    }

    public List<String> getRgb2List() {
        return this.rgb2List;
    }

    public void setRgb2List(List<String> rgb2List) {
        this.rgb2List = rgb2List;
    }

}
