
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "assessIssueRisk"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AssessIssueRiskInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Applicant applicant;
    private java.lang.Number currentRiskAppetite;

    public AssessIssueRiskInput_() {
    }

    public AssessIssueRiskInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicant = input_.get("Applicant");
            setApplicant(type.Applicant.toApplicant(null));
            Object currentRiskAppetite = input_.get("Current risk appetite");
            setCurrentRiskAppetite((java.lang.Number)currentRiskAppetite);
        }
    }

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

}
