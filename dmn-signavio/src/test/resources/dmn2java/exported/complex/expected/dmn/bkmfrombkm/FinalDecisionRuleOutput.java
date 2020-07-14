
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "finalDecision"})
public class FinalDecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String finalDecision;

    public FinalDecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("finalDecision")
    public String getFinalDecision() {
        return this.finalDecision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("finalDecision")
    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalDecisionRuleOutput other = (FinalDecisionRuleOutput) o;
        if (this.getFinalDecision() != null ? !this.getFinalDecision().equals(other.getFinalDecision()) : other.getFinalDecision() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getFinalDecision() != null ? this.getFinalDecision().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", finalDecision='%s'", finalDecision));
        result_.append(")");
        return result_.toString();
    }
}
