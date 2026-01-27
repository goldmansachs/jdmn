
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "assessIssue"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AssessIssueInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number currentRiskAppetite;
    private java.lang.Number priorIssue_iterator;

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
