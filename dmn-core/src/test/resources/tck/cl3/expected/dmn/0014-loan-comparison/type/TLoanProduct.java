package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tLoanProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TLoanProduct extends com.gs.dmn.runtime.DMNType {
    static TLoanProduct toTLoanProduct(Object other) {
        if (other == null) {
            return null;
        } else if (TLoanProduct.class.isAssignableFrom(other.getClass())) {
            return (TLoanProduct)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TLoanProductImpl result_ = new TLoanProductImpl();
            result_.setLenderName((String)((com.gs.dmn.runtime.Context)other).get("lenderName"));
            result_.setRate((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("rate"));
            result_.setPoints((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("points"));
            result_.setFee((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("fee"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTLoanProduct(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TLoanProduct.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    String getLenderName();

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    java.math.BigDecimal getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("points")
    java.math.BigDecimal getPoints();

    @com.fasterxml.jackson.annotation.JsonGetter("fee")
    java.math.BigDecimal getFee();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("lenderName", getLenderName());
        context.put("rate", getRate());
        context.put("points", getPoints());
        context.put("fee", getFee());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TLoanProduct other = (TLoanProduct) o;
        if (this.getFee() != null ? !this.getFee().equals(other.getFee()) : other.getFee() != null) return false;
        if (this.getLenderName() != null ? !this.getLenderName().equals(other.getLenderName()) : other.getLenderName() != null) return false;
        if (this.getPoints() != null ? !this.getPoints().equals(other.getPoints()) : other.getPoints() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getFee() != null ? this.getFee().hashCode() : 0);
        result = 31 * result + (this.getLenderName() != null ? this.getLenderName().hashCode() : 0);
        result = 31 * result + (this.getPoints() != null ? this.getPoints().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("fee=" + getFee());
        result_.append(", lenderName=" + getLenderName());
        result_.append(", points=" + getPoints());
        result_.append(", rate=" + getRate());
        result_.append("}");
        return result_.toString();
    }
}
