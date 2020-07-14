
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "product"})
public class ProductRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal product;

    public ProductRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("product")
    public java.math.BigDecimal getProduct() {
        return this.product;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("product")
    public void setProduct(java.math.BigDecimal product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductRuleOutput other = (ProductRuleOutput) o;
        if (this.getProduct() != null ? !this.getProduct().equals(other.getProduct()) : other.getProduct() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getProduct() != null ? this.getProduct().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", product='%s'", product));
        result_.append(")");
        return result_.toString();
    }
}
