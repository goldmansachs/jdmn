
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decide"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecideInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String properties_iterator;

    public DecideInput_() {
    }

    public DecideInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object properties_iterator = input_.get("Properties");
            setProperties_iterator((String)properties_iterator);
        }
    }

    public String getProperties_iterator() {
        return this.properties_iterator;
    }

    public void setProperties_iterator(String properties_iterator) {
        this.properties_iterator = properties_iterator;
    }

}
