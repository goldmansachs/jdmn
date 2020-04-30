
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "RoutingRules"})
public class RoutingRulesRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String routingRules;
    private Integer routingRulesPriority;

    public RoutingRulesRuleOutput(boolean matched) {
        super(matched);
    }

    public String getRoutingRules() {
        return this.routingRules;
    }
    public void setRoutingRules(String routingRules) {
        this.routingRules = routingRules;
    }

    public Integer getRoutingRulesPriority() {
        return this.routingRulesPriority;
    }
    public void setRoutingRulesPriority(Integer routingRulesPriority) {
        this.routingRulesPriority = routingRulesPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutingRulesRuleOutput other = (RoutingRulesRuleOutput) o;
        if (this.getRoutingRules() != null ? !this.getRoutingRules().equals(other.getRoutingRules()) : other.getRoutingRules() != null) return false;
        if (this.getRoutingRulesPriority() != null ? !this.getRoutingRulesPriority().equals(other.getRoutingRulesPriority()) : other.getRoutingRulesPriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getRoutingRules() != null ? this.getRoutingRules().hashCode() : 0);
        result = 31 * result + (this.getRoutingRulesPriority() != null ? this.getRoutingRulesPriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", routingRules='%s'", routingRules));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> routingRulesPairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            routingRulesPairs.add(new com.gs.dmn.runtime.Pair(((RoutingRulesRuleOutput)matchedResult_).getRoutingRules(), ((RoutingRulesRuleOutput)matchedResult_).getRoutingRulesPriority()));
        });
        routingRulesPairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(true);
            output_.setRoutingRules(routingRulesPairs.get(i).getLeft());
            output_.setRoutingRulesPriority(routingRulesPairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
