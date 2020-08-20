
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "pick"})
public class PickRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String pick;

    public PickRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("pick")
    public String getPick() {
        return this.pick;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("pick")
    public void setPick(String pick) {
        this.pick = pick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PickRuleOutput other = (PickRuleOutput) o;
        if (this.getPick() != null ? !this.getPick().equals(other.getPick()) : other.getPick() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPick() != null ? this.getPick().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", pick='%s'", pick));
        result_.append(")");
        return result_.toString();
    }
}
