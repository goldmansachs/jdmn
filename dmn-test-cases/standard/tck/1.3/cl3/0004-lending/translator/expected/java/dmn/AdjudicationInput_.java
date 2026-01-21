
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Adjudication"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AdjudicationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private type.TApplicantData applicantData;
        private type.TBureauData bureauData;
        private String supportingDocuments;

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
