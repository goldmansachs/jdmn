
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "partB"})
public class PartBRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String partB;

    public PartBRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("partB")
    public String getPartB() {
        return this.partB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("partB")
    public void setPartB(String partB) {
        this.partB = partB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartBRuleOutput other = (PartBRuleOutput) o;
        if (this.getPartB() != null ? !this.getPartB().equals(other.getPartB()) : other.getPartB() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPartB() != null ? this.getPartB().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", partB='%s'", partB));
        result_.append(")");
        return result_.toString();
    }
}
