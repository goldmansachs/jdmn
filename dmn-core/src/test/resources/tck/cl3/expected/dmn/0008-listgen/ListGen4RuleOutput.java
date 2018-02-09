
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "listGen4"})
public class ListGen4RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String listGen4;

    public ListGen4RuleOutput(boolean matched) {
        super(matched);
    }

    public String getListGen4() {
        return this.listGen4;
    }
    public void setListGen4(String listGen4) {
        this.listGen4 = listGen4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListGen4RuleOutput other = (ListGen4RuleOutput) o;
        if (this.getListGen4() != null ? !this.getListGen4().equals(other.getListGen4()) : other.getListGen4() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getListGen4() != null ? this.getListGen4().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", listGen4='%s'", listGen4));
        result_.append(")");
        return result_.toString();
    }
}
