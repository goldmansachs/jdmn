
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decision"})
public class DecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String output1;
    private Integer output1Priority;
    private String output2;
    private Integer output2Priority;

    public DecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("output1")
    public String getOutput1() {
        return this.output1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("output1")
    public void setOutput1(String output1) {
        this.output1 = output1;
    }

    public Integer getOutput1Priority() {
        return this.output1Priority;
    }
    public void setOutput1Priority(Integer output1Priority) {
        this.output1Priority = output1Priority;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("output2")
    public String getOutput2() {
        return this.output2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("output2")
    public void setOutput2(String output2) {
        this.output2 = output2;
    }

    public Integer getOutput2Priority() {
        return this.output2Priority;
    }
    public void setOutput2Priority(Integer output2Priority) {
        this.output2Priority = output2Priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecisionRuleOutput other = (DecisionRuleOutput) o;
        if (this.getOutput1() != null ? !this.getOutput1().equals(other.getOutput1()) : other.getOutput1() != null) return false;
        if (this.getOutput1Priority() != null ? !this.getOutput1Priority().equals(other.getOutput1Priority()) : other.getOutput1Priority() != null) return false;
        if (this.getOutput2() != null ? !this.getOutput2().equals(other.getOutput2()) : other.getOutput2() != null) return false;
        if (this.getOutput2Priority() != null ? !this.getOutput2Priority().equals(other.getOutput2Priority()) : other.getOutput2Priority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getOutput1() != null ? this.getOutput1().hashCode() : 0);
        result = 31 * result + (this.getOutput1Priority() != null ? this.getOutput1Priority().hashCode() : 0);
        result = 31 * result + (this.getOutput2() != null ? this.getOutput2().hashCode() : 0);
        result = 31 * result + (this.getOutput2Priority() != null ? this.getOutput2Priority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", output1='%s'", output1));
        result_.append(String.format(", output2='%s'", output2));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> output1Pairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            output1Pairs.add(new com.gs.dmn.runtime.Pair(((DecisionRuleOutput)matchedResult_).getOutput1(), ((DecisionRuleOutput)matchedResult_).getOutput1Priority()));
        });
        output1Pairs.sort(new com.gs.dmn.runtime.PairComparator());
        List<com.gs.dmn.runtime.Pair<String, Integer>> output2Pairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            output2Pairs.add(new com.gs.dmn.runtime.Pair(((DecisionRuleOutput)matchedResult_).getOutput2(), ((DecisionRuleOutput)matchedResult_).getOutput2Priority()));
        });
        output2Pairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            DecisionRuleOutput output_ = new DecisionRuleOutput(true);
            output_.setOutput1(output1Pairs.get(i).getLeft());
            output_.setOutput1Priority(output1Pairs.get(i).getRight());
            output_.setOutput2(output2Pairs.get(i).getLeft());
            output_.setOutput2Priority(output2Pairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
