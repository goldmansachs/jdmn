
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "describeAgesList"})
public class DescribeAgesListRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String listDescription;

    public DescribeAgesListRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("listDescription")
    public String getListDescription() {
        return this.listDescription;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("listDescription")
    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescribeAgesListRuleOutput other = (DescribeAgesListRuleOutput) o;
        if (this.getListDescription() != null ? !this.getListDescription().equals(other.getListDescription()) : other.getListDescription() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getListDescription() != null ? this.getListDescription().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", listDescription='%s'", listDescription));
        result_.append(")");
        return result_.toString();
    }
}
