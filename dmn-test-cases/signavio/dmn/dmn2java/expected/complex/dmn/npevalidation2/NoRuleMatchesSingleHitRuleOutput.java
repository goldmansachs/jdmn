
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "noRuleMatchesSingleHit"})
public class NoRuleMatchesSingleHitRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean noRuleMatchesSingleHit;

    public NoRuleMatchesSingleHitRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("noRuleMatchesSingleHit")
    public Boolean getNoRuleMatchesSingleHit() {
        return this.noRuleMatchesSingleHit;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("noRuleMatchesSingleHit")
    public void setNoRuleMatchesSingleHit(Boolean noRuleMatchesSingleHit) {
        this.noRuleMatchesSingleHit = noRuleMatchesSingleHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoRuleMatchesSingleHitRuleOutput other = (NoRuleMatchesSingleHitRuleOutput) o;
        if (this.getNoRuleMatchesSingleHit() != null ? !this.getNoRuleMatchesSingleHit().equals(other.getNoRuleMatchesSingleHit()) : other.getNoRuleMatchesSingleHit() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getNoRuleMatchesSingleHit() != null ? this.getNoRuleMatchesSingleHit().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", noRuleMatchesSingleHit='%s'", noRuleMatchesSingleHit));
        result_.append(")");
        return result_.toString();
    }
}
