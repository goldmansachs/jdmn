
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "topDecision"})
public class TopDecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean topDecision;

    public TopDecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("topDecision")
    public Boolean getTopDecision() {
        return this.topDecision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("topDecision")
    public void setTopDecision(Boolean topDecision) {
        this.topDecision = topDecision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopDecisionRuleOutput other = (TopDecisionRuleOutput) o;
        if (this.getTopDecision() != null ? !this.getTopDecision().equals(other.getTopDecision()) : other.getTopDecision() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTopDecision() != null ? this.getTopDecision().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", topDecision='%s'", topDecision));
        result_.append(")");
        return result_.toString();
    }
}
