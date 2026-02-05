
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "BureauCallTypeTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BureauCallTypeTableInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String preBureauRiskCategory;

    public BureauCallTypeTableInput_() {
    }

    public BureauCallTypeTableInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object preBureauRiskCategory = input_.get("Pre-bureauRiskCategory");
            setPreBureauRiskCategory((String)preBureauRiskCategory);
        }
    }

    public String getPreBureauRiskCategory() {
        return this.preBureauRiskCategory;
    }

    public void setPreBureauRiskCategory(String preBureauRiskCategory) {
        this.preBureauRiskCategory = preBureauRiskCategory;
    }

}
