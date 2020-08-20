
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "partC"})
public class PartCRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String partC;

    public PartCRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("partC")
    public String getPartC() {
        return this.partC;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("partC")
    public void setPartC(String partC) {
        this.partC = partC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartCRuleOutput other = (PartCRuleOutput) o;
        if (this.getPartC() != null ? !this.getPartC().equals(other.getPartC()) : other.getPartC() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPartC() != null ? this.getPartC().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", partC='%s'", partC));
        result_.append(")");
        return result_.toString();
    }
}
