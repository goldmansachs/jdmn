
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "describeAgesList"})
public class DescribeAgesListRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String describeAgesList;

    public DescribeAgesListRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("describeAgesList")
    public String getDescribeAgesList() {
        return this.describeAgesList;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("describeAgesList")
    public void setDescribeAgesList(String describeAgesList) {
        this.describeAgesList = describeAgesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescribeAgesListRuleOutput other = (DescribeAgesListRuleOutput) o;
        if (this.getDescribeAgesList() != null ? !this.getDescribeAgesList().equals(other.getDescribeAgesList()) : other.getDescribeAgesList() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDescribeAgesList() != null ? this.getDescribeAgesList().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", describeAgesList='%s'", describeAgesList));
        result_.append(")");
        return result_.toString();
    }
}
