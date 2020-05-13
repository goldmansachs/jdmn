
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "EligibilityRules"})
public class EligibilityRulesRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String eligibilityRules;
    private Integer eligibilityRulesPriority;

    public EligibilityRulesRuleOutput(boolean matched) {
        super(matched);
    }

    public String getEligibilityRules() {
        return this.eligibilityRules;
    }
    public void setEligibilityRules(String eligibilityRules) {
        this.eligibilityRules = eligibilityRules;
    }

    public Integer getEligibilityRulesPriority() {
        return this.eligibilityRulesPriority;
    }
    public void setEligibilityRulesPriority(Integer eligibilityRulesPriority) {
        this.eligibilityRulesPriority = eligibilityRulesPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EligibilityRulesRuleOutput other = (EligibilityRulesRuleOutput) o;
        if (this.getEligibilityRules() != null ? !this.getEligibilityRules().equals(other.getEligibilityRules()) : other.getEligibilityRules() != null) return false;
        if (this.getEligibilityRulesPriority() != null ? !this.getEligibilityRulesPriority().equals(other.getEligibilityRulesPriority()) : other.getEligibilityRulesPriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getEligibilityRules() != null ? this.getEligibilityRules().hashCode() : 0);
        result = 31 * result + (this.getEligibilityRulesPriority() != null ? this.getEligibilityRulesPriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", eligibilityRules='%s'", eligibilityRules));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> eligibilityRulesPairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            eligibilityRulesPairs.add(new com.gs.dmn.runtime.Pair(((EligibilityRulesRuleOutput)matchedResult_).getEligibilityRules(), ((EligibilityRulesRuleOutput)matchedResult_).getEligibilityRulesPriority()));
        });
        eligibilityRulesPairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(true);
            output_.setEligibilityRules(eligibilityRulesPairs.get(i).getLeft());
            output_.setEligibilityRulesPriority(eligibilityRulesPairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
