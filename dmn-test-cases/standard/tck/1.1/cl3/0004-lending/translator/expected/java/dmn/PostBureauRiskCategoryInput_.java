
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Post-bureauRiskCategory"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PostBureauRiskCategoryInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;
    private type.TBureauData bureauData;

    public PostBureauRiskCategoryInput_() {
    }

    public PostBureauRiskCategoryInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
            Object bureauData = input_.get("BureauData");
            setBureauData(type.TBureauData.toTBureauData(bureauData));
        }
    }

    public type.TApplicantData getApplicantData() {
        return this.applicantData;
    }

    public void setApplicantData(type.TApplicantData applicantData) {
        this.applicantData = applicantData;
    }

    public type.TBureauData getBureauData() {
        return this.bureauData;
    }

    public void setBureauData(type.TBureauData bureauData) {
        this.bureauData = bureauData;
    }

}
