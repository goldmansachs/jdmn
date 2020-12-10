
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "partA"})
public class PartARuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String partA;

    public PartARuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("partA")
    public String getPartA() {
        return this.partA;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("partA")
    public void setPartA(String partA) {
        this.partA = partA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartARuleOutput other = (PartARuleOutput) o;
        if (this.getPartA() != null ? !this.getPartA().equals(other.getPartA()) : other.getPartA() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPartA() != null ? this.getPartA().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", partA='%s'", partA));
        result_.append(")");
        return result_.toString();
    }
}
