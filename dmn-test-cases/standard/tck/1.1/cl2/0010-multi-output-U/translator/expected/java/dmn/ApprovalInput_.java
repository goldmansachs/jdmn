
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Approval"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApprovalInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number age;
    private String riskCategory;
    private Boolean isAffordable;

    public ApprovalInput_() {
    }

    public ApprovalInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object age = input_.get("Age");
            setAge((java.lang.Number)age);
            Object riskCategory = input_.get("RiskCategory");
            setRiskCategory((String)riskCategory);
            Object isAffordable = input_.get("isAffordable");
            setIsAffordable((Boolean)isAffordable);
        }
    }

    public java.lang.Number getAge() {
        return this.age;
    }

    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    public String getRiskCategory() {
        return this.riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public Boolean getIsAffordable() {
        return this.isAffordable;
    }

    public void setIsAffordable(Boolean isAffordable) {
        this.isAffordable = isAffordable;
    }

}
