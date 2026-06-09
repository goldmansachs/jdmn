
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "ApplicationRiskScore"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApplicationRiskScoreInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;

    public ApplicationRiskScoreInput_() {
    }

    public ApplicationRiskScoreInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
        }
    }

    public type.TApplicantData getApplicantData() {
        return this.applicantData;
    }

    public void setApplicantData(type.TApplicantData applicantData) {
        this.applicantData = applicantData;
    }

}
