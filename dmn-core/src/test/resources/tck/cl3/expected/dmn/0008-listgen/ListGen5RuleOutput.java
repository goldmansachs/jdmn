
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "listGen5"})
public class ListGen5RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String listGen5;

    public ListGen5RuleOutput(boolean matched) {
        super(matched);
    }

    public String getListGen5() {
        return this.listGen5;
    }
    public void setListGen5(String listGen5) {
        this.listGen5 = listGen5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListGen5RuleOutput other = (ListGen5RuleOutput) o;
        if (this.getListGen5() != null ? !this.getListGen5().equals(other.getListGen5()) : other.getListGen5() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getListGen5() != null ? this.getListGen5().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", listGen5='%s'", listGen5));
        result_.append(")");
        return result_.toString();
    }
}
