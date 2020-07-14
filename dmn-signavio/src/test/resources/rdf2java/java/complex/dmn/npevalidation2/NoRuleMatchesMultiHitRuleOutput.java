
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "noRuleMatchesMultiHit"})
public class NoRuleMatchesMultiHitRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean noRuleMatchesMultiHit;

    public NoRuleMatchesMultiHitRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("noRuleMatchesMultiHit")
    public Boolean getNoRuleMatchesMultiHit() {
        return this.noRuleMatchesMultiHit;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("noRuleMatchesMultiHit")
    public void setNoRuleMatchesMultiHit(Boolean noRuleMatchesMultiHit) {
        this.noRuleMatchesMultiHit = noRuleMatchesMultiHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoRuleMatchesMultiHitRuleOutput other = (NoRuleMatchesMultiHitRuleOutput) o;
        if (this.getNoRuleMatchesMultiHit() != null ? !this.getNoRuleMatchesMultiHit().equals(other.getNoRuleMatchesMultiHit()) : other.getNoRuleMatchesMultiHit() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getNoRuleMatchesMultiHit() != null ? this.getNoRuleMatchesMultiHit().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", noRuleMatchesMultiHit='%s'", noRuleMatchesMultiHit));
        result_.append(")");
        return result_.toString();
    }
}
