
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decision"})
public class DecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal output;

    public DecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("output")
    public java.math.BigDecimal getOutput() {
        return this.output;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("output")
    public void setOutput(java.math.BigDecimal output) {
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecisionRuleOutput other = (DecisionRuleOutput) o;
        if (this.getOutput() != null ? !this.getOutput().equals(other.getOutput()) : other.getOutput() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getOutput() != null ? this.getOutput().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", output='%s'", output));
        result_.append(")");
        return result_.toString();
    }
}
