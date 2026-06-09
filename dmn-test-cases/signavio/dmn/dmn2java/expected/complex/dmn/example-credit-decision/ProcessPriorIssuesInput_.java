
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "processPriorIssues"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ProcessPriorIssuesInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Applicant applicant;

    public ProcessPriorIssuesInput_() {
    }

    public ProcessPriorIssuesInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicant = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#applicant");
            setApplicant(type.Applicant.toApplicant(null));
        }
    }

    public type.Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(type.Applicant applicant) {
        this.applicant = applicant;
    }

}
