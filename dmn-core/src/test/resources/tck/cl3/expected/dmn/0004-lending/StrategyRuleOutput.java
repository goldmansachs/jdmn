
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "Strategy"})
public class StrategyRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String strategy;

    public StrategyRuleOutput(boolean matched) {
        super(matched);
    }

    public String getStrategy() {
        return this.strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StrategyRuleOutput other = (StrategyRuleOutput) o;
        if (this.getStrategy() != null ? !this.getStrategy().equals(other.getStrategy()) : other.getStrategy() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getStrategy() != null ? this.getStrategy().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", strategy='%s'", strategy));
        result_.append(")");
        return result_.toString();
    }
}
