
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "describeAgesList"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DescribeAgesListInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> ages;

    public DescribeAgesListInput_() {
    }

    public DescribeAgesListInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object ages = input_.get("http://www.provider.com/dmn/1.1/diagram/5417bfd1893048bc9ca18c51aa11b7f0.xml#ages");
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
