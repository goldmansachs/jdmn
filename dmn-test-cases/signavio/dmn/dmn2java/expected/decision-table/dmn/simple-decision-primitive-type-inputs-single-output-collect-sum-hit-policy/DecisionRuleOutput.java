
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decision"})
public class DecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal decision;

    public DecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("decision")
    public java.math.BigDecimal getDecision() {
        return this.decision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("decision")
    public void setDecision(java.math.BigDecimal decision) {
        this.decision = decision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecisionRuleOutput other = (DecisionRuleOutput) o;
        if (this.getDecision() != null ? !this.getDecision().equals(other.getDecision()) : other.getDecision() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDecision() != null ? this.getDecision().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", decision='%s'", decision));
        result_.append(")");
        return result_.toString();
    }
}
