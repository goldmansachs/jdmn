
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "removeall2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Removeall2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String rgb1;
    private List<String> rgb1List;

    public Removeall2Input_() {
    }

    public Removeall2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object rgb1 = input_.get("rgb1");
            setRgb1((String)rgb1);
            Object rgb1List = input_.get("rgb1 list");
            setRgb1List((List<String>)rgb1List);
        }
    }

    public String getRgb1() {
        return this.rgb1;
    }

    public void setRgb1(String rgb1) {
        this.rgb1 = rgb1;
    }

    public List<String> getRgb1List() {
        return this.rgb1List;
    }

    public void setRgb1List(List<String> rgb1List) {
        this.rgb1List = rgb1List;
    }

}
