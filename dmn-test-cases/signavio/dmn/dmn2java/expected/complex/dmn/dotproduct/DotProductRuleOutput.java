
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dotProduct"})
public class DotProductRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal dotProduct2;
    private String outputMessage;

    public DotProductRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dotProduct2")
    public java.math.BigDecimal getDotProduct2() {
        return this.dotProduct2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dotProduct2")
    public void setDotProduct2(java.math.BigDecimal dotProduct2) {
        this.dotProduct2 = dotProduct2;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("outputMessage")
    public String getOutputMessage() {
        return this.outputMessage;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("outputMessage")
    public void setOutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DotProductRuleOutput other = (DotProductRuleOutput) o;
        if (this.getDotProduct2() != null ? !this.getDotProduct2().equals(other.getDotProduct2()) : other.getDotProduct2() != null) return false;
        if (this.getOutputMessage() != null ? !this.getOutputMessage().equals(other.getOutputMessage()) : other.getOutputMessage() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDotProduct2() != null ? this.getDotProduct2().hashCode() : 0);
        result = 31 * result + (this.getOutputMessage() != null ? this.getOutputMessage().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dotProduct2='%s'", dotProduct2));
        result_.append(String.format(", outputMessage='%s'", outputMessage));
        result_.append(")");
        return result_.toString();
    }
}
