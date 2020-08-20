
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "decide"})
public class DecideRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String decide;

    public DecideRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("decide")
    public String getDecide() {
        return this.decide;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("decide")
    public void setDecide(String decide) {
        this.decide = decide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecideRuleOutput other = (DecideRuleOutput) o;
        if (this.getDecide() != null ? !this.getDecide().equals(other.getDecide()) : other.getDecide() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDecide() != null ? this.getDecide().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", decide='%s'", decide));
        result_.append(")");
        return result_.toString();
    }
}
