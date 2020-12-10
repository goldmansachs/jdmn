
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "outputExecutionAnalysisResult"})
public class OutputExecutionAnalysisResultRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String outputExecutionAnalysisResult;

    public OutputExecutionAnalysisResultRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("outputExecutionAnalysisResult")
    public String getOutputExecutionAnalysisResult() {
        return this.outputExecutionAnalysisResult;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("outputExecutionAnalysisResult")
    public void setOutputExecutionAnalysisResult(String outputExecutionAnalysisResult) {
        this.outputExecutionAnalysisResult = outputExecutionAnalysisResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputExecutionAnalysisResultRuleOutput other = (OutputExecutionAnalysisResultRuleOutput) o;
        if (this.getOutputExecutionAnalysisResult() != null ? !this.getOutputExecutionAnalysisResult().equals(other.getOutputExecutionAnalysisResult()) : other.getOutputExecutionAnalysisResult() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getOutputExecutionAnalysisResult() != null ? this.getOutputExecutionAnalysisResult().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", outputExecutionAnalysisResult='%s'", outputExecutionAnalysisResult));
        result_.append(")");
        return result_.toString();
    }
}
