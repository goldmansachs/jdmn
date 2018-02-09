
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "priceGt10"})
public class PriceGt10RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean priceGt10;

    public PriceGt10RuleOutput(boolean matched) {
        super(matched);
    }

    public Boolean getPriceGt10() {
        return this.priceGt10;
    }
    public void setPriceGt10(Boolean priceGt10) {
        this.priceGt10 = priceGt10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceGt10RuleOutput other = (PriceGt10RuleOutput) o;
        if (this.getPriceGt10() != null ? !this.getPriceGt10().equals(other.getPriceGt10()) : other.getPriceGt10() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPriceGt10() != null ? this.getPriceGt10().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", priceGt10='%s'", priceGt10));
        result_.append(")");
        return result_.toString();
    }
}
