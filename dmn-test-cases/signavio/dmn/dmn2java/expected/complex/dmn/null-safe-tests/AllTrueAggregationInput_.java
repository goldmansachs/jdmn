
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "allTrueAggregation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AllTrueAggregationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<Boolean> booleanList;

    public AllTrueAggregationInput_() {
    }

    public AllTrueAggregationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanList = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanList");
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
