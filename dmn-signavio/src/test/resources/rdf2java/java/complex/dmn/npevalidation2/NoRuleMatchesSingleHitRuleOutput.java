
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "noRuleMatchesSingleHit"})
public class NoRuleMatchesSingleHitRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean noRuleMatches;

    public NoRuleMatchesSingleHitRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("noRuleMatches")
    public Boolean getNoRuleMatches() {
        return this.noRuleMatches;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("noRuleMatches")
    public void setNoRuleMatches(Boolean noRuleMatches) {
        this.noRuleMatches = noRuleMatches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoRuleMatchesSingleHitRuleOutput other = (NoRuleMatchesSingleHitRuleOutput) o;
        if (this.getNoRuleMatches() != null ? !this.getNoRuleMatches().equals(other.getNoRuleMatches()) : other.getNoRuleMatches() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getNoRuleMatches() != null ? this.getNoRuleMatches().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", noRuleMatches='%s'", noRuleMatches));
        result_.append(")");
        return result_.toString();
    }
}
