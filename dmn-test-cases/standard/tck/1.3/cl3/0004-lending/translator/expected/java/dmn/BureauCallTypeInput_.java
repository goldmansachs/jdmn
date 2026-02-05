
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "BureauCallType"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BureauCallTypeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;

    public BureauCallTypeInput_() {
    }

    public BureauCallTypeInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("ApplicantData");
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
