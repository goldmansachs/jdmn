
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Adjudication"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AdjudicationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;
    private type.TBureauData bureauData;
    private String supportingDocuments;

    public AdjudicationInput_() {
    }

    public AdjudicationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
            Object bureauData = input_.get("BureauData");
            setBureauData(type.TBureauData.toTBureauData(bureauData));
            Object supportingDocuments = input_.get("SupportingDocuments");
            setSupportingDocuments((String)supportingDocuments);
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

    public String getSupportingDocuments() {
        return this.supportingDocuments;
    }

    public void setSupportingDocuments(String supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
    }

}
