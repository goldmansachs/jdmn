
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "sort3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Sort3Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> stringList;

    public Sort3Input_() {
    }

    public Sort3Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object stringList = input_.get("stringList");
            setStringList((List<String>)stringList);
        }
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

}
