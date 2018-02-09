
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dateCompare1"})
public class DateCompare1RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean dateCompare1;

    public DateCompare1RuleOutput(boolean matched) {
        super(matched);
    }

    public Boolean getDateCompare1() {
        return this.dateCompare1;
    }
    public void setDateCompare1(Boolean dateCompare1) {
        this.dateCompare1 = dateCompare1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateCompare1RuleOutput other = (DateCompare1RuleOutput) o;
        if (this.getDateCompare1() != null ? !this.getDateCompare1().equals(other.getDateCompare1()) : other.getDateCompare1() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDateCompare1() != null ? this.getDateCompare1().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dateCompare1='%s'", dateCompare1));
        result_.append(")");
        return result_.toString();
    }
}
