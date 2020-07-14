
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "makeCreditDecision"})
public class MakeCreditDecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String makeCreditDecision;

    public MakeCreditDecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("makeCreditDecision")
    public String getMakeCreditDecision() {
        return this.makeCreditDecision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("makeCreditDecision")
    public void setMakeCreditDecision(String makeCreditDecision) {
        this.makeCreditDecision = makeCreditDecision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MakeCreditDecisionRuleOutput other = (MakeCreditDecisionRuleOutput) o;
        if (this.getMakeCreditDecision() != null ? !this.getMakeCreditDecision().equals(other.getMakeCreditDecision()) : other.getMakeCreditDecision() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getMakeCreditDecision() != null ? this.getMakeCreditDecision().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", makeCreditDecision='%s'", makeCreditDecision));
        result_.append(")");
        return result_.toString();
    }
}
