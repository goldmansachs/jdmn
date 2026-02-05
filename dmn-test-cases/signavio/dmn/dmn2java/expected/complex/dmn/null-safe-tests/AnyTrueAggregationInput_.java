
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "anyTrueAggregation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AnyTrueAggregationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<Boolean> booleanList;

    public AnyTrueAggregationInput_() {
    }

    public AnyTrueAggregationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanList = input_.get("booleanList");
            setBooleanList((List<Boolean>)booleanList);
        }
    }

    public List<Boolean> getBooleanList() {
        return this.booleanList;
    }

    public void setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

}
