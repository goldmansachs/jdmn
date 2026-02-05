
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "RoutingRules"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RoutingRulesInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String postBureauRiskCategory;
    private Boolean postBureauAffordability;
    private Boolean bankrupt;
    private java.lang.Number creditScore;

    public RoutingRulesInput_() {
    }

    public RoutingRulesInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object postBureauRiskCategory = input_.get("Post-bureauRiskCategory");
            setPostBureauRiskCategory((String)postBureauRiskCategory);
            Object postBureauAffordability = input_.get("Post-bureauAffordability");
            setPostBureauAffordability((Boolean)postBureauAffordability);
            Object bankrupt = input_.get("Bankrupt");
            setBankrupt((Boolean)bankrupt);
            Object creditScore = input_.get("CreditScore");
            setCreditScore((java.lang.Number)creditScore);
        }
    }

    public String getPostBureauRiskCategory() {
        return this.postBureauRiskCategory;
    }

    public void setPostBureauRiskCategory(String postBureauRiskCategory) {
        this.postBureauRiskCategory = postBureauRiskCategory;
    }

    public Boolean getPostBureauAffordability() {
        return this.postBureauAffordability;
    }

    public void setPostBureauAffordability(Boolean postBureauAffordability) {
        this.postBureauAffordability = postBureauAffordability;
    }

    public Boolean getBankrupt() {
        return this.bankrupt;
    }

    public void setBankrupt(Boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public java.lang.Number getCreditScore() {
        return this.creditScore;
    }

    public void setCreditScore(java.lang.Number creditScore) {
        this.creditScore = creditScore;
    }

}
