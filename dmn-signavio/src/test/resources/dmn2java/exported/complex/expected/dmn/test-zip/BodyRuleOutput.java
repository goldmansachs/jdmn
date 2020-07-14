
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "body"})
public class BodyRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal body;

    public BodyRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("body")
    public java.math.BigDecimal getBody() {
        return this.body;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("body")
    public void setBody(java.math.BigDecimal body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BodyRuleOutput other = (BodyRuleOutput) o;
        if (this.getBody() != null ? !this.getBody().equals(other.getBody()) : other.getBody() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getBody() != null ? this.getBody().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", body='%s'", body));
        result_.append(")");
        return result_.toString();
    }
}
