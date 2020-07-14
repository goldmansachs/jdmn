
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decision"})
public class DecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String output;
    private Integer outputPriority;

    public DecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("output")
    public String getOutput() {
        return this.output;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("output")
    public void setOutput(String output) {
        this.output = output;
    }

    public Integer getOutputPriority() {
        return this.outputPriority;
    }
    public void setOutputPriority(Integer outputPriority) {
        this.outputPriority = outputPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecisionRuleOutput other = (DecisionRuleOutput) o;
        if (this.getOutput() != null ? !this.getOutput().equals(other.getOutput()) : other.getOutput() != null) return false;
        if (this.getOutputPriority() != null ? !this.getOutputPriority().equals(other.getOutputPriority()) : other.getOutputPriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getOutput() != null ? this.getOutput().hashCode() : 0);
        result = 31 * result + (this.getOutputPriority() != null ? this.getOutputPriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", output='%s'", output));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> outputPairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            outputPairs.add(new com.gs.dmn.runtime.Pair(((DecisionRuleOutput)matchedResult_).getOutput(), ((DecisionRuleOutput)matchedResult_).getOutputPriority()));
        });
        outputPairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            DecisionRuleOutput output_ = new DecisionRuleOutput(true);
            output_.setOutput(outputPairs.get(i).getLeft());
            output_.setOutputPriority(outputPairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
