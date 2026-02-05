
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "EligibilityRules"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class EligibilityRulesInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String preBureauRiskCategory;
    private Boolean preBureauAffordability;
    private java.lang.Number age;

    public EligibilityRulesInput_() {
    }

    public EligibilityRulesInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object preBureauRiskCategory = input_.get("Pre-bureauRiskCategory");
            setPreBureauRiskCategory((String)preBureauRiskCategory);
            Object preBureauAffordability = input_.get("Pre-bureauAffordability");
            setPreBureauAffordability((Boolean)preBureauAffordability);
            Object age = input_.get("Age");
            setAge((java.lang.Number)age);
        }
    }

    public String getPreBureauRiskCategory() {
        return this.preBureauRiskCategory;
    }

    public void setPreBureauRiskCategory(String preBureauRiskCategory) {
        this.preBureauRiskCategory = preBureauRiskCategory;
    }

    public Boolean getPreBureauAffordability() {
        return this.preBureauAffordability;
    }

    public void setPreBureauAffordability(Boolean preBureauAffordability) {
        this.preBureauAffordability = preBureauAffordability;
    }

    public java.lang.Number getAge() {
        return this.age;
    }

    public void setAge(java.lang.Number age) {
        this.age = age;
    }

}
