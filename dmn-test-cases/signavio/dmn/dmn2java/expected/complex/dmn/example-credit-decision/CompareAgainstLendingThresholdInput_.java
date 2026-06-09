
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compareAgainstLendingThreshold"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompareAgainstLendingThresholdInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Applicant applicant;
    private java.lang.Number currentRiskAppetite;
    private java.lang.Number lendingThreshold;

    public CompareAgainstLendingThresholdInput_() {
    }

    public CompareAgainstLendingThresholdInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicant = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#applicant");
            setApplicant(type.Applicant.toApplicant(null));
            Object currentRiskAppetite = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#currentRiskAppetite");
            setCurrentRiskAppetite((java.lang.Number)currentRiskAppetite);
            Object lendingThreshold = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#lendingThreshold");
            setLendingThreshold((java.lang.Number)lendingThreshold);
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

    public java.lang.Number getLendingThreshold() {
        return this.lendingThreshold;
    }

    public void setLendingThreshold(java.lang.Number lendingThreshold) {
        this.lendingThreshold = lendingThreshold;
    }

}
