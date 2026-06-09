
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "assessIssue"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AssessIssueInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number currentRiskAppetite;
    private java.lang.Number priorIssue_iterator;

    public AssessIssueInput_() {
    }

    public AssessIssueInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object currentRiskAppetite = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#currentRiskAppetite");
            setCurrentRiskAppetite((java.lang.Number)currentRiskAppetite);
            Object priorIssue_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#priorIssue_iterator");
            setPriorIssue_iterator((java.lang.Number)priorIssue_iterator);
        }
    }

    public java.lang.Number getCurrentRiskAppetite() {
        return this.currentRiskAppetite;
    }

    public void setCurrentRiskAppetite(java.lang.Number currentRiskAppetite) {
        this.currentRiskAppetite = currentRiskAppetite;
    }

    public java.lang.Number getPriorIssue_iterator() {
        return this.priorIssue_iterator;
    }

    public void setPriorIssue_iterator(java.lang.Number priorIssue_iterator) {
        this.priorIssue_iterator = priorIssue_iterator;
    }

}
