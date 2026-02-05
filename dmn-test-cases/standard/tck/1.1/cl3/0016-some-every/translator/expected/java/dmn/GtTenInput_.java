
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "gtTen"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GtTenInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number theNumber;

    public GtTenInput_() {
    }

    public GtTenInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object theNumber = input_.get("theNumber");
            setTheNumber((java.lang.Number)theNumber);
        }
    }

    public java.lang.Number getTheNumber() {
        return this.theNumber;
    }

    public void setTheNumber(java.lang.Number theNumber) {
        this.theNumber = theNumber;
    }

}
