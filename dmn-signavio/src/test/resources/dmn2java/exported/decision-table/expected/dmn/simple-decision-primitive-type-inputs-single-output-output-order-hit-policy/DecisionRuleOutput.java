
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decision"})
public class DecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String decision;
    private Integer decisionPriority;

    public DecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("decision")
    public String getDecision() {
        return this.decision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("decision")
    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Integer getDecisionPriority() {
        return this.decisionPriority;
    }
    public void setDecisionPriority(Integer decisionPriority) {
        this.decisionPriority = decisionPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecisionRuleOutput other = (DecisionRuleOutput) o;
        if (this.getDecision() != null ? !this.getDecision().equals(other.getDecision()) : other.getDecision() != null) return false;
        if (this.getDecisionPriority() != null ? !this.getDecisionPriority().equals(other.getDecisionPriority()) : other.getDecisionPriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDecision() != null ? this.getDecision().hashCode() : 0);
        result = 31 * result + (this.getDecisionPriority() != null ? this.getDecisionPriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", decision='%s'", decision));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> decisionPairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            decisionPairs.add(new com.gs.dmn.runtime.Pair(((DecisionRuleOutput)matchedResult_).getDecision(), ((DecisionRuleOutput)matchedResult_).getDecisionPriority()));
        });
        decisionPairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            DecisionRuleOutput output_ = new DecisionRuleOutput(true);
            output_.setDecision(decisionPairs.get(i).getLeft());
            output_.setDecisionPriority(decisionPairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
