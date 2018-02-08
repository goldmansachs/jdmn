
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dateCompare2"})
public class DateCompare2RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean dateCompare2;

    public DateCompare2RuleOutput(boolean matched) {
        super(matched);
    }

    public Boolean getDateCompare2() {
        return this.dateCompare2;
    }
    public void setDateCompare2(Boolean dateCompare2) {
        this.dateCompare2 = dateCompare2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateCompare2RuleOutput other = (DateCompare2RuleOutput) o;
        if (this.getDateCompare2() != null ? !this.getDateCompare2().equals(other.getDateCompare2()) : other.getDateCompare2() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDateCompare2() != null ? this.getDateCompare2().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dateCompare2='%s'", dateCompare2));
        result_.append(")");
        return result_.toString();
    }
}
