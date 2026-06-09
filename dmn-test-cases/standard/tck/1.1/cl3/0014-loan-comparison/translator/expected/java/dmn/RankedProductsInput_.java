
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "RankedProducts"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RankedProductsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number requestedAmt;

    public RankedProductsInput_() {
    }

    public RankedProductsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object requestedAmt = input_.get("http://www.trisotech.com/definitions/_56c7d4a5-e6db-4bba-ac5f-dc082a16f719#RequestedAmt");
            setRequestedAmt((java.lang.Number)requestedAmt);
        }
    }

    public java.lang.Number getRequestedAmt() {
        return this.requestedAmt;
    }

    public void setRequestedAmt(java.lang.Number requestedAmt) {
        this.requestedAmt = requestedAmt;
    }

}
