
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "allTogether"})
public class AllTogetherRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String allTogether;

    public AllTogetherRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("allTogether")
    public String getAllTogether() {
        return this.allTogether;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("allTogether")
    public void setAllTogether(String allTogether) {
        this.allTogether = allTogether;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllTogetherRuleOutput other = (AllTogetherRuleOutput) o;
        if (this.getAllTogether() != null ? !this.getAllTogether().equals(other.getAllTogether()) : other.getAllTogether() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getAllTogether() != null ? this.getAllTogether().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", allTogether='%s'", allTogether));
        result_.append(")");
        return result_.toString();
    }
}
