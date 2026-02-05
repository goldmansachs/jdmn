
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "statistical"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class StatisticalInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> numberList;

    public StatisticalInput_() {
    }

    public StatisticalInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberList = input_.get("numberList");
            setNumberList((List<java.lang.Number>)numberList);
        }
    }

    public List<java.lang.Number> getNumberList() {
        return this.numberList;
    }

    public void setNumberList(List<java.lang.Number> numberList) {
        this.numberList = numberList;
    }

}
