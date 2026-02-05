
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "describeAgesList"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DescribeAgesListInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> ages;

    public DescribeAgesListInput_() {
    }

    public DescribeAgesListInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object ages = input_.get("ages");
            setAges((List<java.lang.Number>)ages);
        }
    }

    public List<java.lang.Number> getAges() {
        return this.ages;
    }

    public void setAges(List<java.lang.Number> ages) {
        this.ages = ages;
    }

}
