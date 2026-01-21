
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Approval Status"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApprovalStatusInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.lang.Number age;
        private String riskCategory;
        private Boolean isAffordable;

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
