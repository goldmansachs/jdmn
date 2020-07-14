
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "compoundOutputCompoundDecision"})
public class CompoundOutputCompoundDecisionRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String firstOutput;
    private String secondOutput;

    public CompoundOutputCompoundDecisionRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("firstOutput")
    public String getFirstOutput() {
        return this.firstOutput;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("firstOutput")
    public void setFirstOutput(String firstOutput) {
        this.firstOutput = firstOutput;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("secondOutput")
    public String getSecondOutput() {
        return this.secondOutput;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("secondOutput")
    public void setSecondOutput(String secondOutput) {
        this.secondOutput = secondOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundOutputCompoundDecisionRuleOutput other = (CompoundOutputCompoundDecisionRuleOutput) o;
        if (this.getFirstOutput() != null ? !this.getFirstOutput().equals(other.getFirstOutput()) : other.getFirstOutput() != null) return false;
        if (this.getSecondOutput() != null ? !this.getSecondOutput().equals(other.getSecondOutput()) : other.getSecondOutput() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getFirstOutput() != null ? this.getFirstOutput().hashCode() : 0);
        result = 31 * result + (this.getSecondOutput() != null ? this.getSecondOutput().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", firstOutput='%s'", firstOutput));
        result_.append(String.format(", secondOutput='%s'", secondOutput));
        result_.append(")");
        return result_.toString();
    }
}
