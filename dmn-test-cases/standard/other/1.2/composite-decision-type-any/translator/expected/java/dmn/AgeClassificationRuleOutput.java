
import java.util.*;

@jakarta.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ageClassification"})
public class AgeClassificationRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String cls;
    private java.math.BigDecimal discount;

    public AgeClassificationRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("cls")
    public String getCls() {
        return this.cls;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("cls")
    public void setCls(String cls) {
        this.cls = cls;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("discount")
    public java.math.BigDecimal getDiscount() {
        return this.discount;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("discount")
    public void setDiscount(java.math.BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgeClassificationRuleOutput other = (AgeClassificationRuleOutput) o;
        if (this.getCls() != null ? !this.getCls().equals(other.getCls()) : other.getCls() != null) return false;
        if (this.getDiscount() != null ? !this.getDiscount().equals(other.getDiscount()) : other.getDiscount() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getCls() != null ? this.getCls().hashCode() : 0);
        result = 31 * result + (this.getDiscount() != null ? this.getDiscount().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", cls='%s'", cls));
        result_.append(String.format(", discount='%s'", discount));
        result_.append(")");
        return result_.toString();
    }
}
