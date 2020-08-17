
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "processPriorIssues"})
public class ProcessPriorIssuesRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal processPriorIssues;

    public ProcessPriorIssuesRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("processPriorIssues")
    public java.math.BigDecimal getProcessPriorIssues() {
        return this.processPriorIssues;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("processPriorIssues")
    public void setProcessPriorIssues(java.math.BigDecimal processPriorIssues) {
        this.processPriorIssues = processPriorIssues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessPriorIssuesRuleOutput other = (ProcessPriorIssuesRuleOutput) o;
        if (this.getProcessPriorIssues() != null ? !this.getProcessPriorIssues().equals(other.getProcessPriorIssues()) : other.getProcessPriorIssues() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getProcessPriorIssues() != null ? this.getProcessPriorIssues().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", processPriorIssues='%s'", processPriorIssues));
        result_.append(")");
        return result_.toString();
    }
}
