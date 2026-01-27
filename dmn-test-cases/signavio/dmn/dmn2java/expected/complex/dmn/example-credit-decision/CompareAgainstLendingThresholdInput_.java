
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compareAgainstLendingThreshold"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompareAgainstLendingThresholdInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Applicant applicant;
    private java.lang.Number currentRiskAppetite;
    private java.lang.Number lendingThreshold;

    public type.Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(type.Applicant applicant) {
        this.applicant = applicant;
    }

    public java.lang.Number getCurrentRiskAppetite() {
        return this.currentRiskAppetite;
    }

    public void setCurrentRiskAppetite(java.lang.Number currentRiskAppetite) {
        this.currentRiskAppetite = currentRiskAppetite;
    }

    public java.lang.Number getLendingThreshold() {
        return this.lendingThreshold;
    }

    public void setLendingThreshold(java.lang.Number lendingThreshold) {
        this.lendingThreshold = lendingThreshold;
    }

}
